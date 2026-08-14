#include <bits/stdc++.h>
using namespace std;

struct User {
    string name;
    int budget;
    int energy;
    unordered_set<string> tags;
    bool active = true;
};

struct Activity {
    int id;
    string name;
    int cost;
    int duration;
    int energy;
    string tag;
};

struct Input {
    int N, D, H;
    vector<User> users;
    map<int, Activity> activities;     // ordered by id
    vector<string> events;             // verbatim event lines
};

static Input readInput() {
    Input in;
    cin >> in.N >> in.D >> in.H;
    in.users.resize(in.N);
    for (int i = 0; i < in.N; i++) {
        int k;
        cin >> in.users[i].name >> in.users[i].budget >> in.users[i].energy >> k;
        for (int j = 0; j < k; j++) {
            string t; cin >> t;
            in.users[i].tags.insert(t);
        }
        in.users[i].active = true;
    }
    int A; cin >> A;
    for (int i = 0; i < A; i++) {
        Activity a;
        cin >> a.id >> a.name >> a.cost >> a.duration >> a.energy >> a.tag;
        in.activities[a.id] = a;
    }
    int E; cin >> E;
    cin.ignore();
    for (int i = 0; i < E; i++) {
        string line;
        getline(cin, line);
        // trim
        while (!line.empty() && (line.back() == '\r' || line.back() == ' ')) line.pop_back();
        in.events.push_back(line);
    }
    return in;
}

/** Format a single day line exactly per spec. Use REST if ids is empty. */
static string formatDay(int day, vector<int> ids, int cost, int sat) {
    if (ids.empty()) {
        return "Day " + to_string(day) + ": REST | cost=0 satisfaction=0";
    }
    sort(ids.begin(), ids.end());
    string s = "Day " + to_string(day) + ": ";
    for (size_t i = 0; i < ids.size(); i++) {
        if (i) s += ' ';
        s += to_string(ids[i]);
    }
    s += " | cost=" + to_string(cost) + " satisfaction=" + to_string(sat);
    return s;
}



// =========================================================================
// YOUR CODE GOES HERE.
//
// Implement solve(const Input& in) and return the FULL output string
// (including the trailing newline) that the judge will diff against
// the expected output.
//
// Helpers available from the Head section:
//   - formatDay(day, ids, cost, sat)  -> properly formatted "Day X: ..." line
//   - in.users        : vector<User>           ({name, budget, energy, tags, active})
//   - in.activities   : map<int,Activity>      ({id, name, cost, duration, energy, tag})
//   - in.events       : vector<string>         verbatim event lines, e.g.
//                       "DROP 2 Bob", "WEATHER 3 NATURE",
//                       "FATIGUE 2 Alice 5", "BUDGET 4 Alice 20"
// =========================================================================


struct actScore {
    int activityID;
    int cost;              // tie-breaker
    int satisfactionScore; // profit
    int duration;          // weight
};
map<int, actScore> filterAndScore(const Input& in, const unordered_set<int>& BLids) {
    int minBudget = INT_MAX, minStamina = INT_MAX;
    
    bool anyActive = false;
    for(const User& user: in.users) {
        if(!user.active) continue;
        
        anyActive = true;
        if(user.budget < minBudget) minBudget = user.budget;
        if(user.energy < minStamina) minStamina = user.energy;        
    }
    if(!anyActive) return {};
    map<int, actScore> filtered;
    
    for(const auto& pair: in.activities) {
        const Activity& act = pair.second;
        // skip blacklisted IDs
        if(BLids.count(act.id)) continue; 
        
        if(act.cost <= minBudget && act.energy <= minStamina) {
            actScore as;
            
            as.activityID = act.id;
            as.cost = act.cost;            
            as.satisfactionScore = 0;
            as.duration = act.duration;
            
            for(const User& user: in.users) {
                if(!user.active) continue;
                if(user.tags.count(act.tag) > 0)
                    as.satisfactionScore++;
            }
            filtered[act.id] = as;
        }
    }
    return filtered;
}

struct KnapsackState {
    int satisfaction = 0;
    int cost = 0;
    vector<int> ids;
};
bool isBetter(const KnapsackState& a, const KnapsackState& b) {
    if(a.satisfaction != b.satisfaction)
        return a.satisfaction > b.satisfaction;
    if(a.cost != b.cost)
        return a.cost < b.cost;
    return a.ids < b.ids;
}
KnapsackState knapsack(const map<int, actScore>& filteredActivities, int hoursAvailable) {
    vector<KnapsackState> dp(hoursAvailable + 1);
    
    for(const auto& pair: filteredActivities) {
        const actScore& act = pair.second;
        
        for(int w = hoursAvailable; w >= act.duration; --w) {
            KnapsackState candidate = dp[w - act.duration];
            
            candidate.satisfaction += act.satisfactionScore;
            candidate.cost += act.cost;
            candidate.ids.push_back(act.activityID);
            
            if(isBetter(candidate, dp[w]))
                dp[w] = candidate;
        }
    }
    KnapsackState bestOverall = dp[0];
    for(int w = 1; w <= hoursAvailable; ++w) {
        if(isBetter(dp[w], bestOverall))
            bestOverall = dp[w];
    }   
    return bestOverall;
}


static string solve(const Input& in) {
    string out;
    out += "=== PLAN ===\n";

    Input moddedIn = in;
    vector<KnapsackState> plan(in.D + 1);
    map<int, unordered_set<int>> weatherBlacklist;

    auto runPlan = [&](int startDay) {
        unordered_set<int> usedIDs;
        
        for (int day = 1; day < startDay; day++) 
            for (int id : plan[day].ids) usedIDs.insert(id);
        
        for (int day = startDay; day <= moddedIn.D; day++) {
            unordered_set<int> bl = usedIDs;
            for (int id : weatherBlacklist[day]) bl.insert(id);
            
            map<int, actScore> filtered = filterAndScore(moddedIn, bl);
            plan[day] = knapsack(filtered, moddedIn.H);
            
            for (int id : plan[day].ids) usedIDs.insert(id);
        }
    };

    // build the initial D-day plan.  
    runPlan(1);
    for (int day = 1; day <= in.D; day++) {
        out += formatDay(day, plan[day].ids, plan[day].cost, plan[day].satisfaction) + "\n";
    }

    // Process events
    for (int event = 1; event <= (int)in.events.size(); event++) {
        stringstream ss(in.events[event - 1]);
        string command; ss >> command; 
        int startDay; ss >> startDay;
        
        if (command == "WEATHER") {
            string BLtag; ss >> BLtag;
            for (const auto& pair : moddedIn.activities) {
                if (pair.second.tag == BLtag) 
                    weatherBlacklist[startDay].insert(pair.first);
            }
        } else if (command == "DROP") {
            string name; ss >> name;
            for (User& user : moddedIn.users) 
                if (user.name == name) user.active = false;
        } else if (command == "FATIGUE") {
            string name; ss >> name;
            int energy; ss >> energy;
            for (User& user : moddedIn.users) 
                if (user.name == name) user.energy = energy;
        } else if (command == "BUDGET") {
            string name; ss >> name;
            int budget; ss >> budget;
            for (User& user : moddedIn.users) 
                if (user.name == name) user.budget = budget;
        }
        out += "=== EVENT " + to_string(event) + ": " + in.events[event - 1] + " ===\n";
        
        if (startDay >= 1 && startDay <= in.D) {
            runPlan(startDay);
            for (int day = startDay; day <= in.D; day++) 
                out += formatDay(day, plan[day].ids, plan[day].cost, 
                                      plan[day].satisfaction) + "\n";
        }
    }    
    return out;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    Input in = readInput();
    cout << solve(in);
    return 0;
}
