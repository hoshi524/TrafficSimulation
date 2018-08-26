#include <bits/stdc++.h>
using namespace std;

constexpr int N = 1000;
constexpr double junctionCost = 100;
double roadCost, railCost;
int to[N];
double points[N][2];

int main() {
  {  // input
    cin >> roadCost >> railCost;
    for (int i = 0; i < N; ++i) {
      cin >> to[i] >> points[i][0] >> points[i][1];
    }
  }
  {  // output
    /*
    ジャンクションは作成せず、目的の家に直通の道路を作成します
    */
    cout << 0 << endl;
    cout << N << endl;
    for (int i = 0; i < N; ++i) {
      cout << i << " " << to[i] << " " << 0 << endl;
    }
    for (int i = 0; i < N; ++i) {
      cout << 1 << " " << to[i] << endl;
    }
  }
}