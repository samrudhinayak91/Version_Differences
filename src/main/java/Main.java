/**
 * Created by samrudhinayak on 9/23/16.
 */

import com.scitools.understand.*;
import com.scitools.understand.Database;
import org.jgrapht.*;
//import org.jgrapht.alg.isomorphism.VF2GraphMappingIterator;
import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.alg.isomorphism.IsomorphicGraphMapping;
import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    static DirectedGraph<String, DefaultEdge> callgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    static DirectedGraph<String, DefaultEdge> callgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    static DirectedGraph<String, DefaultEdge> depgraph1 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    static DirectedGraph<String, DefaultEdge> depgraph2 = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    static SimpleDirectedGraph<String, DefaultEdge> inhergraph1 = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    static SimpleDirectedGraph<String, DefaultEdge> inhergraph2 = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

    public static void main(String[] args) {
        String dirname1;
        Scanner in1 = new Scanner(System.in);
        System.out.println("Please enter the directory path for first udb");
        dirname1 = in1.nextLine();
        String dirname2;
        Scanner in2 = new Scanner(System.in);
        System.out.println("Please enter the directory path for second udb");
        dirname2 = in2.nextLine();
        try {
//Open the Understand Database
            Database db = Understand.open(dirname1);
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
            Database db = Understand.open(dirname2);
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
            Database db = Understand.open(dirname1);
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
            Database db = Understand.open(dirname2);
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
        try {
//Open the Understand Database
            Database db = Understand.open(dirname1);
// Get a list of all classes and interfaces
            Entity[] funcs = db.ents("class ~unknown ~unresolved, interface ~unknown ~unresolved");
            for (Entity e : funcs) {
                inhergraph1.addVertex(e.name().toString());
                ArrayList<String> params = new ArrayList<String>();
                Reference[] parameterRefs = e.refs("implement, extend", "", true);
                for (Reference pRef : parameterRefs) {
                    Entity pEnt = pRef.ent();
                    params.add(pEnt.name().toString());
                }
                for (String a : params) {
                    inhergraph1.addVertex(a);
                    inhergraph1.addEdge(e.name().toString(), a);
                }
            }
        } catch (UnderstandException e) {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
        System.out.println("Call Graph 1    " + callgraph1);
        System.out.println("Call Graph 2    " + callgraph2);
        System.out.println("Dependency Graph 1    " + depgraph1);
        System.out.println("Dependency Graph 2    " + depgraph2);
        //System.out.println("Inheritance Graph 1    " + inhergraph1);
        String isocallgraph=getisocall(callgraph1, callgraph2);
        System.out.println(isocallgraph);
        String isodepgraph=getisodep(depgraph1, depgraph2);
        System.out.println(isodepgraph);
        SimpleDirectedGraph<String, DefaultEdge> inhergraph2 = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        System.out.println("Enter class names seperated by commas for transitive closure");
        Scanner inh=new Scanner(System.in);
        String str = inh.nextLine();
        String[] strArr = str.split(",");
        inhergraph2=gettransitiveclosure(inhergraph1,strArr);
        System.out.println("Graph with transitive closure is : " + inhergraph2);
    }

    public static String getisocall(DirectedGraph<String, DefaultEdge> callgraph1, DirectedGraph<String, DefaultEdge> callgraph2) {
        System.out.println("Call graph differences");
        ArrayList<String> tempstring = new ArrayList<>();
        VF2SubgraphIsomorphismInspector<String, DefaultEdge> iso = new VF2SubgraphIsomorphismInspector<String, DefaultEdge>(callgraph1, callgraph2);
        try {
            Iterator<GraphMapping<String, DefaultEdge>> iterator = iso.getMappings();
            if (iterator.hasNext()) {
                iterator.next();
                IsomorphicGraphMapping<String, DefaultEdge> first = (IsomorphicGraphMapping<String, DefaultEdge>) iterator.next();
                //System.out.println(first.toString());
                Iterator<String> cg1 = callgraph1.vertexSet().iterator();
                while (cg1.hasNext()) {
                    String temp = cg1.next().toString();
                    boolean isisod1 = first.hasVertexCorrespondence(temp);
                    if (isisod1 == false)
                        tempstring.add(temp);
                }
            } else
                tempstring.add("NO mapping exists");

        } catch (NullPointerException E) {
            tempstring.add("Please enter a graph that is a subgraph");
        }
        return tempstring.toString();
    }

    public static String getisodep(DirectedGraph<String, DefaultEdge> depgraph1, DirectedGraph<String, DefaultEdge> depgraph2) {
        System.out.println("Dependency graph differences");
        ArrayList<String> tempstring = new ArrayList<>();
        VF2SubgraphIsomorphismInspector<String, DefaultEdge> isod = new VF2SubgraphIsomorphismInspector<String, DefaultEdge>(depgraph1, depgraph2);
        try {
            Iterator<GraphMapping<String, DefaultEdge>> iteratord = isod.getMappings();
            if (iteratord.hasNext()) {
                IsomorphicGraphMapping<String, DefaultEdge> second = (IsomorphicGraphMapping<String, DefaultEdge>) iteratord.next();
                //System.out.println(second.toString());
                Iterator<String> dg1 = depgraph1.vertexSet().iterator();
                while (dg1.hasNext()) {
                    String temp = dg1.next().toString();
                    boolean isisod1 = second.hasVertexCorrespondence(temp);
                    if (isisod1 == false)
                        tempstring.add(temp);
                }
            } else
                tempstring.add("NO mapping exists");
        } catch (NullPointerException e) {
            tempstring.add("Please enter subgraphs");
        }
        return tempstring.toString();
    }
    public static SimpleDirectedGraph<String, DefaultEdge> gettransitiveclosure(DirectedGraph<String, DefaultEdge> inhergraph1,String[] strArr)
    {
        SimpleDirectedGraph<String, DefaultEdge> inhergraph2= new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        for(int i=0; i<strArr.length;i++)
        {
            inhergraph2.addVertex(strArr[i]);
            //System.out.println(strArr[i]);
        }
        //Object[] inher1=inhergraph1.vertexSet().toArray();
        for(int i=0;i<strArr.length;i++)
        {
            for(int j=0;j<strArr.length;j++) {
                if(inhergraph1.containsEdge(strArr[i],strArr[j]))
                {
                    //System.out.println("Adding edge");
                    inhergraph2.addEdge(strArr[i],strArr[j]);
                }
            }
        }
        TransitiveClosure.INSTANCE.closeSimpleDirectedGraph((SimpleDirectedGraph)inhergraph2);
        return inhergraph2;
    }
    public static DirectedGraph<String, DefaultEdge> getcallgraph1()
    {
        return callgraph1;
    }
    public static DirectedGraph<String, DefaultEdge> getcallgraph2()
    {
        return callgraph2;
    }
    public static DirectedGraph<String, DefaultEdge> getdepgraph1()
    {
        return depgraph1;
    }
    public static DirectedGraph<String, DefaultEdge> getdepgraph2()
    {
        return depgraph2;
    }
}
