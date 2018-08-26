# TrafficSimulation

# 問題文

ある村には家がN個あり、それぞれの家に1人が住んでいます。
彼らはそれぞれある他の家に毎日通っています。

彼らのために道を作成し、経路を伝える必要があります。
あなたの仕事は、それらを満たしながら、道の作成コスト、移動時間を最小化することです。

具体的には、ジャンクション、道路、レールの3種が作成できます。
ジャンクションとは、初期状態からある家を含め、他に新たに作成することもできます。
道路やレールはジャンクション間をつなぐことで作成します。

道路とレールのついて
道路を移動する際にかかる時間は、距離*sqrt(利用人数)になります。
レールは利用人数に依存せず、距離/2の時間がかかります。
また、経路中に1度もレールが含まれない場合は、移動時間が1/2になります。

ジャンクション、道路、レールにはそれぞれコストが設定されています。
ジャンクションコストは個数分のコストがかかります。
道路、レールは作成距離*それぞれのコストがかかります。

移動時間は、それぞれの移動時間の2乗の合計です。
スコアは、作成コスト*移動時間です。(桁が大きいので1Gで割ってます)

詳細は `src/Output.java` で参照できます。

# 制約

```
N = 1000
junctionCost = 10
1 <= roadCost <= 5
5 <= railCost <= 10
```

それぞれのコスト、ジャンクションの座標は浮動小数です。

# 入力

```
T = 目的の家(0-index)
```

```
roadCost railCost
T[1] X[1] Y[1]
T[2] X[2] Y[2]
.
.
T[N] X[N] Y[N]
```

# 出力

```
A = 追加するジャンクション数
B = 作成する道路・レール数
P,Q = つなぐジャンクションのID
G = 0 -> 道路、1 -> レール
C = 経路長
D = 経路
```

```
A
X[1] Y[1]
X[2] Y[2]
.
.
X[A] Y[A]
B
P[1] Q[1] G[1]
P[2] Q[2] G[2]
.
.
P[B] Q[B] G[B]
C[1] D[1] D[1] .. D[C[1]]
C[2] D[2] D[2] .. D[C[2]]
.
.
C[N] D[N] D[N] .. D[C[N]]
```

# 使い方

TrafficSimulation.jar が使えます。

1ケース実行
```
java -jar TrafficSimulation.jar 'run command' 'seed(long int)'
java -jar TrafficSimulation.jar out/main.out 1
```

一応submit機能(100ケースの合計がsubmitされます)
```
java -jar TrafficSimulation.jar submit 'run command' 'your id'
java -jar TrafficSimulation.jar submit out/main.out hoshi524
```

# 順位表

https://s3-ap-northeast-1.amazonaws.com/trafficsimulation-scoreboard/index.html