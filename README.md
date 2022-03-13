# このリソースの目的

SpringBootアプリをECS Fargateで動かしている場合に、jvmの監視ができないため、ECS ExecをEnableにしたクラスターをセットアップしています。
このリソースはそのうちのアプリケーション部分に該当します。

## maven でのコンパイル
mvn install

## Dockerfileからイメージのビルド
docker build --platform amd64 -t akiraabe/spring-boot-docker .

または、、、

## Buildpacksでのイメージビルド
docker image rm b5508de20891 // 古いイメージ削除
mvn spring-boot:build-image
docker image ls
docker tag 0400ea9c7d2e akiraabe/spring-boot-docker:latest
## コンテナ実行
docker run --platform=linux/amd64 -p 80:80 -it --rm --name my-running-app akiraabe/spring-boot-docker

// buildpacksで作った時
docker run -p 80:80 -it --rm --name my-running-app akiraabe/spring-boot-docker:latest

## DockerHubへのアップ
docker login
docker push akiraabe/spring-boot-docker

## jvm監視
$ jps
1 ./app.js

$ jstat -gcutil -t 1 5000

## ECS Exec
https://dev.classmethod.jp/articles/ecs-exec/

## service の設定をみるコマンド
```
aws ecs describe-services --cluster CdkFargateStack-MyCdkClusterCFA0BF3A-PzVTjplTY9xy --services CdkFargateStack-MyCdkServiceE76112C0-Cp5qERsEfalz


```

## ecs execを有効化するコマンド
```
aws ecs update-service \
    --cluster CdkFargateStack-MyCdkClusterCFA0BF3A-0wzNDoDes57V --service CdkFargateStack-MyCdkServiceE76112C0-nAXjC1wKElPw \
    --enable-execute-command

```

## 動いてるタスクの設定をみるコマンド
```shell
aws ecs describe-tasks --cluster CdkFargateStack-MyCdkClusterCFA0BF3A-PzVTjplTY9xy --tasks 6c45fa389174407c962bc267c0f03064
```

## ecs exec
```shell
aws ecs execute-command \
  --cluster CdkFargateStack-MyCdkClusterCFA0BF3A-VRdCHzOAwmyB \
  --task e94b8616852b40f8aaf34713057ba4b0 \
  --container web \
  --interactive \
  --command "/bin/sh"
```

## 初回はセッションマネージャープラグインの導入が必要であった
https://docs.aws.amazon.com/ja_jp/systems-manager/latest/userguide/session-manager-working-with-install-plugin.html#install-plugin-macos


## ECS環境を作るCDKの所在（ECS Execの有効化も組み込み済みです）
/Users/akiraabe/Documents/practice/cdk/cdk-fargate
Qiitaも書きました。

https://qiita.com/akiraabe/items/a031aa49824bc74ed094