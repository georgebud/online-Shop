build: 
	javac *.java
clean:
	rm -rf *.class *.txt
run:
	java Test "./test05" "result05.txt"
	java Test "./test07" "result07.txt"