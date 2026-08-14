// Input : line 1 = T; then per case: line A = root name, line B = compact JSON.
// Output: per-case blocks joined by a line `---`, ending with one '\n'.

#include <bits/stdc++.h>
using namespace std;

enum class JSONtype {
    STRING, NUMBER, BOOLEAN, NULL_TYPE, OBJECT, ARRAY
};
struct JSONnode {
    JSONtype type;
    map<string, JSONnode*> children;
    vector<JSONnode*> elements;
    
    ~JSONnode() {
        for(auto& pair: children) delete pair.second;
        for(auto& element: elements) delete element;
    }
};

class JSONparser {
    const string& JSON;
    size_t pointer;
    
    void skipWhitespace() {
        while(pointer < JSON.size() && isspace(JSON[pointer]))
            pointer++;
    }
    string parseString() {
        pointer++;
        string str = "";
        while(pointer < JSON.size() && JSON[pointer] != '"') {
            if(JSON[pointer] == '\\')
                str += JSON[pointer++];
            str += JSON[pointer++];
        }
        pointer++;
        return str;
    }
    
    public:
    JSONparser(const string& JSONtext):
        JSON(JSONtext), pointer(0) {}
    
    JSONnode* parseValue() {
        skipWhitespace();
        if(pointer >= JSON.size()) return nullptr;
        
        char ch = JSON[pointer];
        JSONnode* node = new JSONnode();
        
        if(ch == '{') {
            node->type = JSONtype::OBJECT;
            pointer++;
            skipWhitespace();
            
            while(pointer < JSON.size() && JSON[pointer] != '}') {
                string key = parseString();
                skipWhitespace();
                pointer++;
                
                node->children[key] = parseValue();
                skipWhitespace();
                
                if(JSON[pointer] == ',') pointer++;
                skipWhitespace();
            }
            pointer++;
        } else if(ch == '[') {
            node->type = JSONtype::ARRAY;
            pointer++;
            skipWhitespace();
            
            while(pointer < JSON.size() && JSON[pointer] != ']') {
                node->elements.push_back(parseValue());
                skipWhitespace();
                
                if(JSON[pointer] == ',') pointer++;
                skipWhitespace();
            }
            pointer++;            
        } else if(ch == '"') {
            node->type = JSONtype::STRING;
            parseString();
        } else if(ch == 't' || ch == 'f') {
            node->type = JSONtype::BOOLEAN;
            
            while(pointer < JSON.size() && isalpha(JSON[pointer])) 
                pointer++;
        } else if(ch == 'n') {
            node->type = JSONtype::NULL_TYPE;
            
            while(pointer < JSON.size() && isalpha(JSON[pointer])) 
                pointer++;            
        } else { // number            
            node->type = JSONtype::NUMBER;
            
            while(pointer < JSON.size() && (isdigit(JSON[pointer]) 
            || JSON[pointer] == '.' || JSON[pointer] == '-'))
                pointer++;
        }
        return node;
    }
};


class TSGenerator {
    map<string, string> interfaces;
    set<string> usedNames;
    
    string capitalize(const string& str) {
        if(str.empty()) return str;
        
        string res = str;
        res[0] = toupper(res[0]);
        return res;
    }
    string getUniqueName(const string& baseName) {
        if(usedNames.find(baseName) == usedNames.end()) {
            usedNames.insert(baseName);
            return baseName;
        }
        int suffix = 2;
        while(true) {
            string candidate = baseName + to_string(suffix);
            if(usedNames.find(candidate) == usedNames.end()) {
                usedNames.insert(candidate);
                return candidate;
            }
            suffix++;
        }
    }
    
    public:
    void generateRoot(JSONnode* root, const string& rootName) {
        usedNames.insert(rootName);
        
        if(!root) return;
        if(root->type == JSONtype::ARRAY) {
            vector<JSONnode*> objects;
            for(auto* element: root->elements) {
                if(element && element->type == JSONtype::OBJECT)
                    objects.push_back(element);
            }
            buildInterface(objects, rootName);
        } else if(root->type == JSONtype::OBJECT) {
            buildInterface({root}, rootName);
        }
    }
    
    string computeUnion(const vector<JSONnode*>& nodes, const string& keyName) {
        bool hasString = false, hasNumber = false, hasBool = false, hasNull = false;
        vector<JSONnode*> objects;
        vector<JSONnode*> arrayElements;
        
        for(auto* node: nodes) {
            if(!node) continue;
            
            switch(node->type) {
                case JSONtype::OBJECT: objects.push_back(node); break;                
                case JSONtype::ARRAY: 
                    for(auto* element: node->elements) 
                        arrayElements.push_back(element);
                    break;
                case JSONtype::STRING: hasString = true; break;
                case JSONtype::NUMBER: hasNumber = true; break;
                case JSONtype::BOOLEAN: hasBool = true; break;
                case JSONtype::NULL_TYPE: hasNull = true; break;
            }
        }
        vector<string> unionTypes;

        if(!objects.empty()) {
            string baseName = capitalize(keyName);
            string uniqueName = getUniqueName(baseName);
            unionTypes.push_back(buildInterface(objects, uniqueName));
        }
        if(!arrayElements.empty()) {
            string elementType = computeUnion(arrayElements, keyName);
            if(elementType.empty())
                unionTypes.push_back("unknown[]");
            else if(elementType.find('|') != string::npos)
                unionTypes.push_back("(" + elementType + ")[]");
            else
                unionTypes.push_back(elementType + "[]");                    
        }            
        if(hasBool) unionTypes.push_back("boolean");
        if(hasNull) unionTypes.push_back("null");
        if(hasNumber) unionTypes.push_back("number");
        if(hasString) unionTypes.push_back("string");

        if(unionTypes.empty()) return "";

        sort(unionTypes.begin(), unionTypes.end());

        string res = unionTypes[0];
        for(size_t i = 1; i < unionTypes.size(); i++)
            res += " | " + unionTypes[i];
        return res;
    }
    
    string buildInterface(const vector<JSONnode*>& objects, const string& name) {
        map<string, vector<JSONnode*>> keyToNodes;
        int totalObjects = objects.size();
        
        for(auto* object: objects) {
            for(auto const& pair: object->children)
                keyToNodes[pair.first].push_back(pair.second);
        }
        
        if(keyToNodes.empty()) {
            interfaces[name] = "export interface " + name + " {}";
            return name;
        }        
        string interfaceDef = "export interface " + name + " {\n";
        
        for(auto const& pair: keyToNodes) {
            const string& key = pair.first;
            const vector<JSONnode*>& nodes = pair.second;
            
            bool isOptional = false;
            if(totalObjects > 0)
                isOptional = ((int)nodes.size() < totalObjects);
            
            string fieldType = computeUnion(nodes, key);
            if(fieldType.empty()) fieldType = "unknown";
            
            interfaceDef += "  " + key + (isOptional? "?" : "") + ": " 
                                 + fieldType + ";\n";
        }
        interfaceDef += "}";
        interfaces[name] = interfaceDef;
        return name;
    }
    
    string getOutput() {
        string result;        
        for(auto const& pair: interfaces) 
            result += pair.second + "\n\n";
        
        while(!result.empty() && result.back() == '\n')
            result.pop_back();
        return result;
    }
};


static string solve(const string& rootName, const string& jsonText) {
    JSONparser parser(jsonText);
    JSONnode* root = parser.parseValue();
    
    if(!root) return "";
    
    TSGenerator gen;
    gen.generateRoot(root, rootName);
    
    string output = gen.getOutput();
    delete root;    
    return output;
}


int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int t;
    cin >> t;
    cin.ignore();

    string out;
    for (int i = 0; i < t; i++) {
        string rootName, jsonText;
        getline(cin, rootName);
        getline(cin, jsonText);
        if (i > 0) out += "\n---\n";
        out += solve(rootName, jsonText);
    }
    out += '\n';

    cout << out;
    return 0;
}