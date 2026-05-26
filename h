[33mcommit 66d02091f8971a411e3b43db8a65c74396dcc223[m[33m ([m[1;36mHEAD[m[33m -> [m[1;32m나명준/8주차[m[33m, [m[1;31morigin/나명준/8주차[m[33m)[m
Author: Nanajun22 <skaudwns3@gmail.com>
Date:   Tue May 26 19:40:47 2026 +0900

    docker-dompose.yml 작성

[1mdiff --git a/.github/workflows/ci.yml b/.github/workflows/ci.yml[m
[1mindex 7814510..803ba9a 100644[m
[1m--- a/.github/workflows/ci.yml[m
[1m+++ b/.github/workflows/ci.yml[m
[36m@@ -16,7 +16,7 @@[m [mjobs:[m
         uses: actions/setup-java@v4[m
         with:[m
           java-version: '21'[m
[31m-          distribution: 'Corretto'[m
[32m+[m[32m          distribution: 'temurin'[m
           cache: 'gradle'[m
 [m
       - name: gradlew test[m
[36m@@ -33,7 +33,7 @@[m [mjobs:[m
         uses: actions/setup-java@v4[m
         with:[m
           java-version: '21'[m
[31m-          distribution: 'Corretto'[m
[32m+[m[32m          distribution: 'temurin'[m
           cache: 'gradle'[m
 [m
       - name: gradlew build[m
