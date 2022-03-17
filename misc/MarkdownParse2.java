// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    static String[] imageExtensions = {".png", ".jpeg", ".gif", ".csv", ".jpg", ".svg", ".pdf"};

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);
            
            // Both pairs of brackets/parens must exist
            if (nextOpenBracket == -1 || nextCloseBracket == -1) break;
            if (openParen == -1 || closeParen == -1) break;

            if (nextCloseBracket > openParen) break;
            if (!checkExtension(markdown.substring(openParen +1, closeParen)) && openParen-nextCloseBracket==1)
            {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
            currentIndex = closeParen + 1;
        }
        return toReturn;
    }

    public static boolean checkExtension(String substring) {
        for (int i = 0; i < imageExtensions.length; ++i) {
            if (substring.contains(imageExtensions[i])) {
                return true;
            }
        }
    return false;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}
