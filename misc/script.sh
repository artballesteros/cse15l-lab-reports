
for file in test-files/*.md;
do
	echo -n "$file "
	java MarkdownParse $file
done
