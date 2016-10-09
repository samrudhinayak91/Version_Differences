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
    @Test
    public void main() throws Exception {
        SimpleDirectedGraph<String,DefaultEdge> testgraph= new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        SimpleDirectedGraph<String,DefaultEdge> samplegraph= new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> callgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> callgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> depgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> depgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        String[] strArr = new String[3];
        strArr[0]="Phrase.LeftCurlyBracketToken";
        strArr[1]="Phrase.Token";
        strArr[2]="lang.Object";
        String projpath1="/Users/samrudhinayak/IdeaProjects/SamTest/src/main/resources/MyUnderstandProjectp1.udb";
        String projpath2="/Users/samrudhinayak/IdeaProjects/SamTest/src/main/resources/MyUnderstandProjectp2.udb";
        try {
//Open the Understand Database
            Database db = Understand.open(projpath1);
// Get a list of all functions and methods
            Entity[] funcs = db.ents("function ~unknown ~unresolved,method ~unknown ~unresolved");
            for (Entity e : funcs) {
                callgraph1.addVertex(e.name().toString());
                ArrayList<String> params = new ArrayList<String>();
                Reference[] parameterRefs = e.refs("call", "function ~unknown ~unresolved,method ~unknown ~unresolved", true);
                for (Reference pRef : parameterRefs) {
                    Entity pEnt = pRef.ent();
                    params.add(pEnt.name().toString());
                }
                for (String a : params) {
                    callgraph1.addVertex(a);
                    callgraph1.addEdge(e.name().toString(), a);
                }
            }
        } catch (UnderstandException e) {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
        try {
//Open the Understand Database
            Database db = Understand.open(projpath2);
// Get a list of all functions and methods
            Entity[] funcs = db.ents("function ~unknown ~unresolved,method ~unknown ~unresolved");
            for (Entity e : funcs) {
                callgraph2.addVertex(e.name().toString());
                ArrayList<String> params = new ArrayList<String>();
                Reference[] parameterRefs = e.refs("call", "function ~unknown ~unresolved,method ~unknown ~unresolved", true);
                for (Reference pRef : parameterRefs) {
                    Entity pEnt = pRef.ent();
                    params.add(pEnt.name().toString());
                }
                for (String a : params) {
                    callgraph2.addVertex(a);
                    callgraph2.addEdge(e.name().toString(), a);
                }
            }
        } catch (UnderstandException e) {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
        try {
//Open the Understand Database
            Database db = Understand.open(projpath1);
// Get a list of all classes
            Entity[] funcs = db.ents("class ~unknown ~unresolved");
            for (Entity e : funcs) {
                depgraph1.addVertex(e.name().toString());
                ArrayList<String> params = new ArrayList<String>();
                Reference[] parameterRefs = e.refs("Couple", " ", true);
                for (Reference pRef : parameterRefs) {
                    Entity pEnt = pRef.ent();
                    params.add(pEnt.name().toString());
                }
                for (String a : params) {
                    depgraph1.addVertex(a);
                    depgraph1.addEdge(e.name().toString(), a);
                }
            }
        } catch (UnderstandException e) {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
        try {
//Open the Understand Database
            Database db = Understand.open(projpath2);
// Get a list of all classes
            Entity[] funcs = db.ents("class ~unknown ~unresolved");
            for (Entity e : funcs) {
                depgraph2.addVertex(e.name().toString());
                ArrayList<String> params = new ArrayList<String>();
                Reference[] parameterRefs = e.refs("Couple", " ", true);
                for (Reference pRef : parameterRefs) {
                    Entity pEnt = pRef.ent();
                    params.add(pEnt.name().toString());
                }
                for (String a : params) {
                    depgraph2.addVertex(a);
                    depgraph2.addEdge(e.name().toString(), a);
                }
            }
        } catch (UnderstandException e) {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
        samplegraph.addVertex("Phrase.LeftCurlyBracketToken");
        samplegraph.addVertex("Phrase.Token");
        samplegraph.addVertex("lang.Object");
        samplegraph.addEdge("Phrase.LeftCurlyBracketToken","Phrase.Token");
        samplegraph.addEdge("Phrase.Token","lang.Object");
        testgraph.addVertex("Phrase.LeftCurlyBracketToken");
        testgraph.addVertex("Phrase.Token");
        testgraph.addVertex("lang.Object");
        testgraph.addEdge("Phrase.LeftCurlyBracketToken","Phrase.Token");
        testgraph.addEdge("Phrase.Token","lang.Object");
        testgraph.addEdge("Phrase.LeftCurlyBracketToken","lang.Object");
        String callgraphdiff="[Formatter.format, ListPhrase.checkNotNull, ListPhrase.checkNotNullOrEmpty, ListPhrase.formatOrThrow, ListPhrase.from, ListPhrase.ListPhrase, ListPhrase.getSize, ListPhrase.joinIterableWithSize, ListPhrase.joinTwoElements, ListPhrase.joinMoreThanTwoElements, Phrase.put, Phrase.putOptional, TextToken.expand, TextToken.getFormattedLength]";
        String depgraphdiff="[asList.(Anon_1), util.AbstractList, phrase.ListPhrase, asList.T, checkNotNull.T, checkNotNullOrEmpty.T, formatOrThrow.T, Formatter.T, join.T, joinIterableWithSize.T, joinMoreThanTwoElements.T, joinTwoElements.T, ListPhrase.Formatter]";
        DirectedGraph<String, DefaultEdge> tdepgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        DirectedGraph<String, DefaultEdge> tdepgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        tdepgraph1=Main.getdepgraph1();
        tdepgraph2=Main.getdepgraph2();
        Assert.assertEquals(Main.getisocall(callgraph1,callgraph2),callgraphdiff);
        Assert.assertEquals(Main.getisodep(depgraph1,depgraph2),depgraphdiff);
        //Assert.assertEquals(Main.getisodep(tdepgraph1,tdepgraph2),depgraphdiff);
        Assert.assertEquals(Main.gettransitiveclosure(samplegraph,strArr).toString().trim(),testgraph.toString().trim());
        //Assert.assertEquals(9,9);
    }

}