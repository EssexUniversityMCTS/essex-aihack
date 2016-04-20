#!/bin/sh
###############################################################################
### Created by Jialin Liu on 17/03/2016                                     ###
### This script is used to build a list of java source and run the program  ###
### Options:                                                                ### 
### -s source_list_filename                                                 ###
### -b build_folder                                                         ###
### -m main_filename                                                        ###
###############################################################################

source_list_filename=sourceList.txt                                        
build_folder=build                                                         
main_filename=battleGameTest.Test
rm -r build

## Loop until all parameters are used up
while [ "$1" != "" ]; do
    case $1 in
        -s | --source ) shift
            source_list_filename=${1}
            ;;
        -b | --build ) shift
            build_folder=${1}
            ;;
        -m | --main ) shift
            main_filename=${1}
            ;;
        * ) exit 1
    esac
    shift
done

## Create the build folder if not exist
mkdir -p ${build_folder}

## Find all the .java files and list their paths in ${source_list_filename}
# find . -name "*.java" > ${source_list_filename}

## Build
javac -d ${build_folder} @${source_list_filename}

## Execute the program by pointing out the file which has method main
java -cp ${build_folder}:**/*.class ${main_filename} -1 1 -2 0 -N 1 -num_actions 10 -macro_actions 1 -screen 1
#jar cvf ${main_filename}.jar ${build_folder}/**/*.class
