import java.util.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     public static String verify(String traversal1, String traversal2, String traversal3) {
          int[][] seqs = new int[3][];
          try {
               seqs[0] = parse(traversal1);
               seqs[1] = parse(traversal2);
               seqs[2] = parse(traversal3);
          } catch (Exception e) {
               return "Invalid traversals";
          }

          // all three must be the same length
          if (seqs[0].length == 0 || seqs[0].length != seqs[1].length || seqs[0].length != seqs[2].length) {
               return "Invalid traversals";
          }

          int[][] perms = { {0,1,2}, {0,2,1}, {1,0,2}, {1,2,0}, {2,0,1}, {2,1,0} };

          for (int[] p : perms) {
               int[] in = seqs[p[0]]; 
               int[] pre = seqs[p[1]]; 
               int[] post = seqs[p[2]]; 

               // check that inorder is stricly ascending
               boolean sorted = true;
               for (int i = 1; i < in.length; i++) {
                    if (in[i] <= in[i - 1]) { 
                         sorted = false; 
                         break; 
                    }
               }

               // inorder not correct, check next permutation
               if (!sorted) {
                    continue;
               }

               // build BST by inserting the candidate preorder sequence
               Node root = null;
               for (int v : pre) {
                    root = insert(root, v);
               }
               
               List<Integer> inList = new ArrayList<>();
               List<Integer> preList = new ArrayList<>();
               List<Integer> postList = new ArrayList<>();
               inorder(root, inList);
               preorder(root, preList);
               postorder(root, postList);

               if (matches(in, inList) && matches(pre, preList) && matches(post, postList)) {
                    String[] roles = new String[3];
                    roles[p[0]] = "Inorder";
                    roles[p[1]] = "Preorder";
                    roles[p[2]] = "Postorder";
                    return "Traversal 1 - " + roles[0] + ", Traversal 2 - " + roles[1] + ", Traversal 3 - " + roles[2];
               }
          }

          return "Invalid traversals";
     }

     // convert string to int arr
     private static int[] parse(String s) {
          if (s == null || s.trim().isEmpty()) {
               throw new IllegalArgumentException();
          } 
          String[] parts = s.trim().split("-");
          int[] arr = new int[parts.length];
          for (int i = 0; i < parts.length; i++) {
               arr[i] = Integer.parseInt(parts[i].trim());
          }
          return arr;
     }

     // check that expected and actual are same
     private static boolean matches(int[] expected, List<Integer> actual) {
          if (expected.length != actual.size()) {
               return false;
          }
          for (int i = 0; i < expected.length; i++) {
               if (expected[i] != actual.get(i)) {
                    return false;
               }
          }
          return true;
     }

     // insert node into bst
     private static Node insert(Node node, int v) {
          if (node == null) {
               return new Node(v);
          }
          if (v < node.val) {
               node.left = insert(node.left, v);
          } else if (v > node.val) {
               node.right = insert(node.right, v);
          }
          return node;
     }

     private static void inorder(Node n, List<Integer> out) {
          if (n == null) {
               return;
          }
          inorder(n.left, out);
          out.add(n.val);
          inorder(n.right, out);
     }

     private static void preorder(Node n, List<Integer> out) {
          if (n == null) {
               return;
          }
          out.add(n.val);
          preorder(n.left, out);
          preorder(n.right, out);
     }

     private static void postorder(Node n, List<Integer> out) {
          if (n == null) {
               return;
          }
          postorder(n.left, out);
          postorder(n.right, out);
          out.add(n.val);
     }
}