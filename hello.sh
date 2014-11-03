bash;
cd hello
git checkout -b $TRAVIS_JOB_NUMBER
git remote rm origin
git remote add origin https://WonderCsabo:${GH_TOKEN}@github.com/WonderCsabo/hello.git
while true; do
  sleep 60;
  now=$(date +"%Y-%m-%d-%H-%M");
  import -display :99 -window root screenshot.$now.png;
  git add screenshot.$now.png;
  git commit -m "screenshots";
  git push --set-upstream origin $TRAVIS_JOB_NUMBER;
done

