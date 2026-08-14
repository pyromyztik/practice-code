#include <iostream>
#include <vector>
#include <chrono>
#include <omp.h>

int main() {
    // 100 million elements is ~800MB, which fits in most RAM
    const long long N = 100000000; 
    std::vector<long long> arr(N, 1);
    
    long long sum_serial = 0;
    long long sum_parallel = 0;

    // --- SERIAL ---
    auto start_s = std::chrono::high_resolution_clock::now();
    for (long long i = 0; i < N; ++i) { // Use long long for i
        sum_serial += arr[i];
    }
    auto end_s = std::chrono::high_resolution_clock::now();

    // --- PARALLEL ---
    auto start_p = std::chrono::high_resolution_clock::now();
    #pragma omp parallel for reduction(+:sum_parallel)
    for (long long i = 0; i < N; ++i) { // Use long long for i
        sum_parallel += arr[i];
    }
    auto end_p = std::chrono::high_resolution_clock::now();

    std::chrono::duration<double, std::milli> duration_s = end_s - start_s;
    std::chrono::duration<double, std::milli> duration_p = end_p - start_p;

    std::cout << "Serial Time:   " << duration_s.count() << " ms" << std::endl;
    std::cout << "Parallel Time: " << duration_p.count() << " ms" << std::endl;

    return 0;
}