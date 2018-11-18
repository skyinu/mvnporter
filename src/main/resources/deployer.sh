#!/bin/sh
groupId=$1
artifactId=$2
version=$3
packaging=$4
file=$5
pomFile=$6
repositoryId=$7
url=$8
echo $groupId
echo $artifactId
echo $version
echo $packaging
echo $file
echo $pomFile
mvn deploy:deploy-file -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version  -Dpackaging=$packaging  -Dfile=$file -DrepositoryId=$repositoryId -Durl=$url -DpomFile=$pomFile