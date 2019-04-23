//Link State Algorithm

/*
	@Tanishq Chaudhary, 2015105
*/
package a3_2015105;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;


public class LS_2015105 {
    static List<String> nodes = new ArrayList<>();
    private static final Integer INT_MAX1 = 100000;
    static HashMap<Pair<String, String>, Integer> edges = new HashMap<>();
	private static final Integer INT_MAX = 100000;
    static HashMap<String, HashMap<String, Integer>> adjList = new HashMap<>();
	
	public static void main(String[] args) {
		
        int l1,k1,m1;
        Scanner scanner = new Scanner(System.in);
        int sentenceLength,sentenceCount;
		String line = new String();
        sentenceCount = 0;

		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
		}
		int valCount = 0;
		String[] vals = line.split(";");

		for (int i = 0; i < vals.length; i++) {
            int val;
            String[] Cost = vals[i].split(", ");
			String[] Node2 = Cost[0].split(":");
			String[] Node1 = Node2[0].split(" ");
			if (nodes.contains(Node1[1]) != true) {
				nodes.add(Node1[1]);
			}
			if (nodes.contains(Node2[1]) != true) {
				nodes.add(Node2[1]);
			}
			val = Integer.parseInt(Cost[1]);
			
			edges.put(new Pair<String, String>(Node1[1], Node2[1]), val);
			edges.put(new Pair<String, String>(Node2[1], Node1[1]), val);

			if (adjList.containsKey(Node1[1]) != true) {
				HashMap<String, Integer> tmp = new HashMap<>();
				tmp.put(Node2[1], val);
				adjList.put(Node1[1], tmp);
			} else {
				HashMap<String, Integer> tmp = adjList.get(Node1[1]);
				tmp.put(Node2[1], val);
				adjList.replace(Node1[1], tmp);
			}

			if (adjList.containsKey(Node2[1]) != true) {
				HashMap<String, Integer> tmp = new HashMap<>();
				tmp.put(Node1[1], val);
				adjList.put(Node2[1], tmp);
			} else {
				HashMap<String, Integer> tmp = adjList.get(Node2[1]);
				tmp.put(Node1[1], val);
				adjList.replace(Node2[1], tmp);
			}
		}

		System.out.println("Nodes");
        int i0 = 0;
		while(i0 < nodes.size()) {
			System.out.print(nodes.get(i0) + " ");
            i0++;
		}
		System.out.println("\n\n");

		System.out.println("Adjacency List");
		Iterator adjIt = adjList.entrySet().iterator();
		while (adjIt.hasNext() != false) {
			Map.Entry pair = (Map.Entry) adjIt.next();
			Iterator valIt = ((HashMap<String, Integer>) pair.getValue()).entrySet().iterator();
			System.out.print(pair.getKey() + ": ");
			while (valIt.hasNext() != false) {
                int flag0 = 0;
				Map.Entry pair2 = (Map.Entry) valIt.next();
                //Printing Adjacency List Pairs
                flag0 = 1;
				System.out.print("[" + pair2.getKey() + "," + pair2.getValue() + "], ");
                flag0 = 1;
			}
			System.out.println("\n");
		}
        System.out.println("\n");

        int i1 = 0;
		while (i1 < nodes.size()) {
			dijkstraShortestPath(nodes.get(i1));
            i1++;
		}

		while (true) {
            String sentence = "";
			System.out.println("\n\nEnter change in edges");
			if (scanner.hasNext()) {
				sentence = scanner.nextLine();
			}
            int flag1 = 0;
			String[] new_vals = sentence.split(";");
            int new_valsCount = 0;
			for (int i = 0; i < new_vals.length; i++) {
            int val;
            String[] new_Cost = new_vals[i].split(", ");
			String[] new_Node2 = new_Cost[0].split(":");
			String[] new_Node1 = new_Node2[0].split(" ");
			if (nodes.contains(new_Node1[1]) != true) {
				nodes.add(new_Node1[1]);
			}
			if (nodes.contains(new_Node2[1]) != true) {
				nodes.add(new_Node2[1]);
			}
			val = Integer.parseInt(new_Cost[1]);
			if(val==-1) {
				val = INT_MAX;
				if (edges.containsKey(new Pair<String, String>(new_Node1[1], new_Node2[1])) == true) {
					edges.remove(new Pair<String, String>(new_Node1[1], new_Node2[1]));
					edges.remove(new Pair<String, String>(new_Node2[1], new_Node1[1]));
					

					//when node 1 aint present
					if (adjList.containsKey(new_Node1[1]) == true) {
						HashMap<String, Integer> tmp = adjList.get(new_Node1[1]);
							tmp.replace(new_Node2[1], INT_MAX);
						adjList.replace(new_Node1[1], tmp);
					}
					
					//when node 2 aint present
					if (adjList.containsKey(new_Node2[1]) == true) {
						HashMap<String, Integer> tmp = adjList.get(new_Node2[1]);
						tmp.replace(new_Node1[1], INT_MAX);
						adjList.replace(new_Node2[1], tmp);
					}
					
					//nodes.remove(new_Node1[1]);
					//nodes.remove(new_Node2[1]);
				}
			}
			else {
				boolean flag = false;
				//when edge aint present
				if (edges.containsKey(new Pair<String, String>(new_Node1[1], new_Node2[1])) != true) {
					flag = true;
					edges.put(new Pair<String, String>(new_Node1[1], new_Node2[1]), val);
					edges.put(new Pair<String, String>(new_Node2[1], new_Node1[1]), val);
				}
				//when edge is present
				else {
					edges.replace(new Pair<String, String>(new_Node1[1], new_Node2[1]), val);
					edges.replace(new Pair<String, String>(new_Node2[1], new_Node1[1]), val);
				}

				//when node 1 aint present
				if (adjList.containsKey(new_Node1[1]) != true) {
					HashMap<String, Integer> tmp = new HashMap<>();
					tmp.put(new_Node2[1], val);
					adjList.put(new_Node1[1], tmp);
				} 
				//when node 1 is present
				else {
					HashMap<String, Integer> tmp = adjList.get(new_Node1[1]);
					//when edge has just been placed
					if (flag != false) {
						tmp.put(new_Node2[1], val);
					} 
					//when edge was already present
					else {
						tmp.replace(new_Node2[1], val);
					}
					adjList.replace(new_Node1[1], tmp);
				}
				
				//when node 2 aint present
				if (adjList.containsKey(new_Node2[1]) != true) {
					HashMap<String, Integer> tmp = new HashMap<>();
					tmp.put(new_Node1[1], val);
					adjList.put(new_Node2[1], tmp);
				} 
				//when node 2 is present
				else {
					HashMap<String, Integer> tmp = adjList.get(new_Node2[1]);
					if (flag != false) {
						tmp.put(new_Node1[1], val);
					} 
					//when edge was already present
					else {
						tmp.replace(new_Node1[1], val);
					}
					adjList.replace(new_Node2[1], tmp);
				}
				}
            }
			int i2 = 0;
			while (i2 < nodes.size()) {
				dijkstraShortestPath(nodes.get(i2));
				i2++;
				
			}
		
    }
	}

	public static void dijkstraShortestPath(String s) {
		System.out.println("Table for Node: " + s);
		System.out.println();
        int sentenceCount = 0;
		PriorityQueue<Pair<String, Integer>> q = new PriorityQueue<>(nodes.size(), new MyComparator());
        int i3 = 0,i4 = 0;
        HashMap<String, Integer> d = new HashMap<>();
        int flag4 = 0;
		HashMap<String, String> parent = new HashMap<>();

        
		while (i3 < nodes.size()) {
			if (nodes.get(i3).equals(s) != true) {
				if (d.containsKey(nodes.get(i3)) != true) {
					d.put(nodes.get(i3), INT_MAX);
				} else {
					d.replace(nodes.get(i3), INT_MAX);
				}
			}
            i3++;
		}
		if (d.containsKey(s) != true) {
			d.put(s, 0);
		} else {
			d.replace(s, 0);
		}

		System.out.println("d[v] (Dist. from s to v) :");
        int flag3 = -1;
		Iterator it = d.entrySet().iterator();
		while (it.hasNext() != false) {
			Map.Entry pair = (Map.Entry) it.next();
            flag3 = 0;
			System.out.println(pair.getKey() + ": " + pair.getValue());
		}
        flag3 = -1;
		System.out.println();

        
		while (i4 < nodes.size()) {
			parent.put(nodes.get(i4), "");
            i4++;
		}
        i4 = 0;
		while (i4 < nodes.size()) {
			if (q.add(new Pair<String, Integer>(nodes.get(i4), d.get(nodes.get(i4))))) {
			}
            i4++;
		}


		while (q.size() >= 1) {
            int count = 0;
			Pair<String, Integer> p = q.poll();
            int s1 = 0;
            int keycount = 0;
            int valuecount = 0;
			String u = p.getLeft();
			Integer d_u = p.getRight();
			Iterator itr = adjList.get(u).entrySet().iterator();
			//System.out.println("LOLK: "+ itr);
            int flag5 = 0;
            if(adjList.get(u).entrySet().iterator() != null) {
			while (itr.hasNext() != false) {
                flag5 = 1;
				Map.Entry mp = (Map.Entry) itr.next();
                keycount = keycount+1;
				String v = (String) mp.getKey();
                valuecount = valuecount+1;
				Integer le = (Integer) mp.getValue();

				if ((le + d_u) < d.get(v)) {
					if (q.remove(new Pair(v, d.get(v))) != false) {
						d.replace(v, d_u + le);
						q.add(new Pair(v, d.get(v)));
                        flag5 = flag5+1;
						parent.replace(v, u);
                        keycount = keycount-1;
	                }}}
            }
			System.out.println("d[v], Parent");
            int temp = 0;
            int key = 1;
			it = d.entrySet().iterator();
			while (it.hasNext() != false) {

				Map.Entry pair = (Map.Entry) it.next();
                temp = temp +1;
				System.out.println(pair.getKey() + ": " + pair.getValue() + ", " + parent.get(pair.getKey()));
			}
            key = 0;
			System.out.println();

		}

		System.out.println("Forwarding table for the Node: " + s);
        int count1 = 0;
        
		System.out.println("Destination: Next Hop");
        int flag6= 0;
		for (int i = 0; i < nodes.size(); i++) {
            count1++;
			String v = nodes.get(i);
			if (v.equals(s) != false) {
				continue;
			}
			String pr = parent.get(v);
			if(pr != "") {
			while (pr.equals(s) != true) {
				v = pr;
                count1 = 0;
				pr = parent.get(v);
			}
            count1 = count1 +1;
			System.out.println(nodes.get(i) + "\t   :  [" + s + ", " + v + "]");
		}
		else {
			System.out.println(nodes.get(i) + "\t   :  [" + s + ", -]");
		}
		}
		System.out.println("\n");
		System.out.println("***************************************************");
        count1 = 0;
        flag6 = 1;
	}
}


class MyComparator implements Comparator<Pair<String, Integer>> {

	@Override
	public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        int compareBit = 0;
		if (o1.getRight() > o2.getRight()) {
			return 1;
		} else if (o1.getRight() < o2.getRight()) {
			return -1;
		}
		return 0;
	}
}