# Android-Skeleton-Project

A starter project with full of important libraries

### Documents [here](https://github.com/kenilt/Android-Skeleton-Project/wiki)

### Getting started
1. Clone this repository
2. Open it with the latest Android Studio

### Manual release
1. Create a tag on Github
2. Check build variant (must be productionRelease)
3. Build -> Build Bundle(s)/APK(s) -> Build Bundle(s)

But Continuous Deployment will take care about deployment, these steps are just in case of CD errors

### Automatic release
Create a tag on Github

### CI/CD:
Here is the CI/CD rules:
- Commit to features branch => run unit tests
- Commit to develop branch => run unit tests, deploy to internal track
- Commit to release-* branch => run unit tests, deploy to alpha track
- Commit to master => run unit tests, deploy to beta track
- Create a tag on master branch => run unit tests, deploy to beta track (can change to production later)

### Release tag template
```
Release title

- Function 1
- Function 2

Version Number: 5000
```

### Setup local git hook (optional)
- Copy and paste the code below to `.git/hooks/commit-msg`
- Remember to also chmod +x this file using `sudo chmod +x commit-msg`
```
#!/bin/sh
# Commit hook for Skeleton

BRANCH_NAME=$(git branch | grep '*' | sed 's/* //')
FILE_NAME=$1
IFS="-"
set $BRANCH_NAME

if [ "$1" == "feature/lx" ]; then
    echo "" >> "$FILE_NAME"
    echo "" >> "$FILE_NAME"
    echo "[#$2]" >> "$FILE_NAME"
fi

if [ "$1" == "hotfix/lx" ]; then
    echo "" >> "$FILE_NAME"
    echo "" >> "$FILE_NAME"
    echo "[#$2]" >> "$FILE_NAME"
fi
```

### Run test coverage report

To run unified test report for both unit test and instrumentation test

`./gradlew clean jacocoTestReport`

OR

`./gradlew jacocoTestReport`

Then the report will be located at `/app/build/reports/jacoco/jacocoTestReport/html/index.html`


### Contribute

- Issue Tracker: https://github.com/kenilt/Android-Skeleton-Project/issues
- Source Code: https://github.com/kenilt/Android-Skeleton-Project

