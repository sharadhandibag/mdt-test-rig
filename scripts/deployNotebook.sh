curl -XPOST -d @jk_prim_1_1.json http://127.0.0.1:8080/api/notebook/import > out.txt
noteid=`cat out.txt | cut -d, -f3 | cut -d: -f2 | cut -d} -f1 | cut -d\" -f2`
echo "uov_prim_1_1.sh,$noteid" > params.txt
curl -XPOST -d @jk_test_1_1.json http://127.0.0.1:8080/api/notebook/import > out.txt
noteid=`cat out.txt | cut -d, -f3 | cut -d: -f2 | cut -d} -f1 | cut -d\" -f2`
echo "uov_test_1_1.sh,$noteid" >> params.txt
