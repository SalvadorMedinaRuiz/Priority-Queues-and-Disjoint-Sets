import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }
    
    private void siftDown(int i) {
    	int maxIndex = i;
    	int leftChild = (2*i)+1; //0 based index
    	int rightChild = (2*i)+2; //0 based index
    	
    	if (leftChild < data.length && data[leftChild] < data[maxIndex]) {
    		maxIndex = leftChild;
    	}
    	if (rightChild < data.length && data[rightChild] < data[maxIndex]) {
    		maxIndex = rightChild;
    	}
    	
    	if (i != maxIndex){
    		int tmp = data[i];
            data[i] = data[maxIndex];
            data[maxIndex] = tmp;
    		swaps.add(new Swap(i, maxIndex));
    		siftDown(maxIndex);
    	}
    	/*
    	 * if (i != maxIndex){
    		swaps.add(new Swap(data[i], data[maxIndex]));
    		siftDown(maxIndex);
    		}
    	 */
    }

    private void generateSwaps() {
      swaps = new ArrayList<Swap>();
      // The following naive implementation just sorts 
      // the given sequence using selection sort algorithm
      // and saves the resulting sequence of swaps.
      // This turns the given array into a heap, 
      // but in the worst case gives a quadratic number of swaps.
      //
      // TODO: replace by a more efficient implementation
      int size = data.length;
      for (int i = (size/2); i >= 0; i--) {
    	  siftDown(i);
      }
      
      /*
      for (int i = 0; i < data.length; ++i) {
        for (int j = i + 1; j < data.length; ++j) {
          if (data[i] > data[j]) {
            swaps.add(new Swap(i, j));
            int tmp = data[i];
            data[i] = data[j];
            data[j] = tmp;
          }
        }
      }
      */
      
      
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
