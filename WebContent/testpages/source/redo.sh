#!/bin/bash
rm -rf result
python convert_2.py 
rm -rf ../b-*
rm -rf ../n-*
mv result/* ..
cp ../answerpages/4/* ../b-4/leaves/ 
cp ../answerpages/4/* ../n-4/leaves/ 
cp ../answerpages/4t/* ../b-4t/leaves/ 
cp ../answerpages/4t/* ../n-4t/leaves/ 
cp ../answerpages/5/* ../b-5/leaves/ 
cp ../answerpages/5/* ../n-5/leaves/ 
cp ../answerpages/5t/* ../b-5t/leaves/ 
cp ../answerpages/5t/* ../n-5t/leaves/ 
cp ../answerpages/6/* ../b-6/leaves/ 
cp ../answerpages/6/* ../n-6/leaves/ 
cp ../answerpages/6t/* ../b-6t/leaves/ 
cp ../answerpages/6t/* ../n-6t/leaves/ 
cp ../answerpages/t2/* ../n-t2/leaves/ 
cp ../answerpages/t2/* ../b-t2/leaves/