import com.scitools.understand.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by samrudhinayak on 10/8/16.
 */
public class MainTest {
    String projpath1="/Users/samrudhinayak/IdeaProjects/SamTest/src/main/resources/MyUnderstandProjectp1.udb";
    String projpath2="/Users/samrudhinayak/IdeaProjects/SamTest/src/main/resources/MyUnderstandProjectp2.udb";
    //test to get create call and dependency graphs and calculate difference between the graphs
    @Test
    public void test1() throws Exception {
        DirectedGraph<String, DefaultEdge> callgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> callgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> depgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> depgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        callgraph1=Main.createcallgraph(projpath1);
        callgraph2=Main.createcallgraph(projpath2);

        depgraph1=Main.createdirgraph(projpath1);
        depgraph2=Main.createdirgraph(projpath2);
        String callgraphdiff="[Formatter.format, ListPhrase.checkNotNull, ListPhrase.checkNotNullOrEmpty, ListPhrase.formatOrThrow, ListPhrase.from, ListPhrase.ListPhrase, ListPhrase.getSize, ListPhrase.joinIterableWithSize, ListPhrase.joinTwoElements, ListPhrase.joinMoreThanTwoElements, Phrase.put, Phrase.putOptional, TextToken.expand, TextToken.getFormattedLength]";
        String depgraphdiff="[asList.(Anon_1), util.AbstractList, phrase.ListPhrase, asList.T, checkNotNull.T, checkNotNullOrEmpty.T, formatOrThrow.T, Formatter.T, join.T, joinIterableWithSize.T, joinMoreThanTwoElements.T, joinTwoElements.T, ListPhrase.Formatter]";
        DirectedGraph<String, DefaultEdge> tdepgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> tdepgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        Assert.assertEquals(Main.getisocall(callgraph1,callgraph2),callgraphdiff);
        Assert.assertEquals(Main.getisodep(depgraph1,depgraph2),depgraphdiff);
    }
    //test to check for transitive closure
    @Test
    public void test2() throws Exception{
        SimpleDirectedGraph<String,DefaultEdge> testgraph= new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        SimpleDirectedGraph<String,DefaultEdge> samplegraph= new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        samplegraph=Main.createinhergraph(projpath1);
        testgraph.addVertex("Phrase.LeftCurlyBracketToken");
        testgraph.addVertex("Phrase.Token");
        testgraph.addVertex("lang.Object");
        testgraph.addEdge("Phrase.LeftCurlyBracketToken","Phrase.Token");
        testgraph.addEdge("Phrase.Token","lang.Object");
        testgraph.addEdge("Phrase.LeftCurlyBracketToken","lang.Object");
        String[] strArr = new String[3];
        strArr[0]="Phrase.LeftCurlyBracketToken";
        strArr[1]="Phrase.Token";
        strArr[2]="lang.Object";
        Assert.assertEquals(Main.gettransitiveclosure(samplegraph,strArr).toString().trim(),testgraph.toString().trim());
    }
    //test to check for no mapping
    @Test
    public void test3() throws Exception {
        DirectedGraph<String, DefaultEdge> callgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> callgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> depgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> depgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        callgraph1=Main.createcallgraph(projpath2);
        callgraph2=Main.createcallgraph(projpath1);
        depgraph1=Main.createdirgraph(projpath2);
        depgraph2=Main.createdirgraph(projpath1);
        String callgraphdiff="[NO mapping exists]";
        String depgraphdiff="[NO mapping exists]";
        Assert.assertEquals(Main.getisocall(callgraph1,callgraph2),callgraphdiff);
        Assert.assertEquals(Main.getisodep(depgraph1,depgraph2),depgraphdiff);
    }

}