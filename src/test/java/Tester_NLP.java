import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uob.oop.HtmlParser;
import uob.oop.NLP;
import uob.oop.Toolkit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tester_NLP {

    @Test
    @Order(1)
    void textCleaning(){
        String strTest = "!\"$%&^%H).,e+ll~'/o/Wor.l,d!";
        String strTest2 = "?poo>r g[]uy--\\";
        String strTest3 = "Hello 你好 Hola Bonjour Hallo Привет こんにちは 안녕하세요 مرحبا Olá Ciao";
        assertEquals("helloworld", NLP.textCleaning(strTest));
        assertEquals("poor guy", NLP.textCleaning(strTest2));
        assertEquals("hello  hola bonjour hallo     ol ciao", NLP.textCleaning(strTest3));
    }

    @Test
    @Order(2)
    void textLemmatization(){
        String strTest = "bananas";
        String strTest2 = "bananas and apples and digging and hated and education and varedes";
        assertEquals("banana", NLP.textLemmatization(strTest));
        assertEquals("banana and appl and digg and hat and education and vared", NLP.textLemmatization(strTest2));
    }

    @Test
    @Order(3)
    void textRemoveStopWords(){
        String strTest = "hello I live in a house and it is in the valleys of wales and I love it";
        String[] stopWordsTest = {"it", "is", "in", "the", "a", "and"};
        assertEquals("hello I live house valleys of wales I love", NLP.removeStopWords(strTest, stopWordsTest));
    }
}
