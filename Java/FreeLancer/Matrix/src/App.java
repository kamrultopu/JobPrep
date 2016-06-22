import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Node{
	private int nRow;
	private int nCol;
	private int nVal;
	private Node rowLink;
	private Node colLink;
	
	public void Transpose(){
		int temp;
		temp = nRow;
		nRow = nCol;
		nCol = temp;
		Node tempNode = rowLink;
		rowLink = colLink;
		colLink = tempNode;
	}
	
	public Node(int row, int col, int val){
		nRow = row;
		nCol = col;
		nVal = val;
		rowLink = null;
		colLink = null;
	}
	public Node(){
		nRow = -1;
		nCol = -1;
		nVal = -1;
		rowLink = null;
		colLink = null;
	}
	
	public String toString() {
		String str = "Node [nRow=" + nRow + ", nCol=" + nCol + ", nVal=" + nVal;
		if( rowLink != null ){
			str = str + ", next Row: " + rowLink.toString();
		}
		if( colLink != null ){
			str = str + ", next Col: " + colLink.toString();
		}
		str = str + "]";
		return str;
	}
	public void SetrowLink(Node node){
		rowLink = node;
	}
	public void SetcolLink(Node node){
		colLink = node;
	}
	public Node getRowLink(){
		return rowLink;
	}
	public Node getColLink(){
		return colLink;
	}
	public int getRow(){
		return nRow;
	}
	public int getCol(){
		return nCol;
	}
	public int getVal(){
		return nVal;
	}
	public void setVal(int value){
		nVal = value;
	}
	public static boolean IsSameMatrixPosition(Node tempA, Node tempB ){
		if( tempA == null || tempB == null ){
			return false;
		}
		if( tempA.getRow() == tempB.getRow() && tempA.nCol == tempB.getCol()){
			return true;
		}
		return false;
	}
}
class Matrix{
	private int nOrder;
	private Node[] rowHeader;
	private Node[] colHeader;
	public Matrix(){
	}
	public Matrix(int order){
		setNorder(order);
	}
	public Node getrowHeader(int index){
		return rowHeader[index];
	}
	public Node getcolHeader(int index){
		return colHeader[index];
	}
	
	public void setrowHeader(int index, Node temp){
		rowHeader[index] = temp;
	}
	public void setcolHeader(int index, Node temp){
		colHeader[index] = temp;
	}
	
	public int getNorder(){
		return nOrder;
	}
	public void setNorder(int order){
		nOrder = order;
		rowHeader = new Node[nOrder];
		colHeader = new Node[nOrder];
	}
	
	public void InitializeByInput(int testCase){
		int[][] testMatrix;
		
		nOrder = 4;
		rowHeader = new Node[nOrder];
		colHeader = new Node[nOrder];
		testMatrix=new int[][]{{8,0,6,0},{0,7,5,0},{3,0,0,0},{0,0,0,9}};
		Node temp = null;
		Node lastrowNode = null;
		Node[] lastcolNode = new Node[nOrder];
		boolean[] rowHeaderFlag = new boolean[nOrder];
		boolean[] colHeaderFlag = new boolean[nOrder];
		Arrays.fill(rowHeaderFlag, false);
		Arrays.fill(colHeaderFlag, false);
		
		if(testCase == 0){
			Scanner sc = new Scanner(System.in);
			System.out.println("give the order of matrix: ");
			nOrder = sc.nextInt();
			testMatrix = new int[nOrder][nOrder];
			for(int i = 0 ; i < nOrder; i++){
				Arrays.fill(testMatrix[i], 0);
			}
			sc.nextLine();
			System.out.println("give the input for the matrix, row, col and value (indexing start from 0): (\"stop\" to stop)");
			int row, col, val;
			while(sc.hasNextInt()){
				row = sc.nextInt();
				col = sc.nextInt();
				val = sc.nextInt();
				testMatrix[row][col] = val;
			}
			sc.close();
		}
		
		for(int i = 0 ; i < nOrder ; i++){
			for(int j = 0 ; j < nOrder ; j++){
				if(testMatrix[i][j] != 0){
					temp = new Node(i,j,testMatrix[i][j]);
					if(rowHeaderFlag[i] != true){
						rowHeaderFlag[i] = true;
						rowHeader[i] = temp;
					}
					if(colHeaderFlag[j] != true){
						colHeaderFlag[j] = true;
						colHeader[j] = temp;
					}
					if(lastrowNode != null){
						lastrowNode.SetrowLink(temp);
					}
					if(lastcolNode[j] != null ){
						lastcolNode[j].SetcolLink(temp);
					}
					lastrowNode = temp;
					lastcolNode[j] = temp;
				}
			}
			lastrowNode = null;
		}
		
		
	}
	
	public void Check(){
		System.out.println("Checking Row");
		for(int i = 0 ; i < rowHeader.length; i++){
			System.out.println("for row i: " + Integer.toString(i) + "    ");
			Node temp = rowHeader[i];
			while(temp != null){
				System.out.println(temp);
				temp = temp.getRowLink();
			}
		}
		System.out.println("Checking Column");
		for(int i = 0 ; i < colHeader.length; i++){
		    System.out.println("for col i: " + Integer.toString(i) + "    ");
			Node temp = colHeader[i];
			while(temp != null){
				System.out.println(temp);
				temp = temp.getColLink();
			}
		}
		
		
	}
	//
	// There are 3 different formula distinguished by nType = {1,2,3}
	//
	public void InitializeByFormula(int nType){
		Node temp = null;
		Node lastrowNode = null;
		//Node lastcolNode = null;
		boolean[] rowHeaderFlag = new boolean[nOrder];
		boolean[] colHeaderFlag = new boolean[nOrder];
		Node[] lastcolNode = new Node[nOrder];
		Arrays.fill(rowHeaderFlag, false);
		Arrays.fill(colHeaderFlag, false);
		switch(nType){
		case 1:
			for(int i = 0 ; i < nOrder ; i++){
				for(int j = 0 ; j < nOrder ; j++){
					if((i+1)==(j+1)){
						temp = new Node(i,j,i+1);
						if(rowHeaderFlag[i] != true){
							rowHeaderFlag[i] = true;
							rowHeader[i] = temp;
						}
						if(colHeaderFlag[j] != true){
							colHeaderFlag[j] = true;
							colHeader[j] = temp;
						}
						if(lastrowNode != null){
							lastrowNode.SetrowLink(temp);
						}
						if(lastcolNode[j] != null ){
							lastcolNode[j].SetcolLink(temp);
						}
						lastrowNode = temp;
						lastcolNode[j] = temp;
					}
				}
				lastrowNode = null;
			}
			break;
		case 2:
			for(int i = 0 ; i < nOrder ; i++){
				for(int j = 0 ; j < nOrder ; j++){
					if((i+1)==((j+2)% nOrder)){
						
						temp = new Node(i,j,(-2*(j+1)-1));
						//System.out.println(temp);
						if(rowHeaderFlag[i] != true){
							rowHeaderFlag[i] = true;
							rowHeader[i] = temp;
						}
						if(colHeaderFlag[j] != true){
							colHeaderFlag[j] = true;
							colHeader[j] = temp;
						}
						if(lastrowNode != null){
							lastrowNode.SetrowLink(temp);
						}
						if(lastcolNode[j] != null ){
							lastcolNode[j].SetcolLink(temp);
						}
						lastrowNode = temp;
						lastcolNode[j] = temp;
					}
				}
				lastrowNode = null;
			}
			break;
		case 3:
			for(int i = 0 ; i < nOrder ; i++){
				for(int j = 0 ; j < nOrder ; j++){
					if((j+1 == 3) || (((i+1)%2==0) && ((j+1)%2==0))){
						if(j+1 ==3){
							temp = new Node(i,j,-(i+1));
						}
						else{
							temp = new Node(i,j,i+j+2);
						}
						
						if(rowHeaderFlag[i] != true){
							rowHeaderFlag[i] = true;
							rowHeader[i] = temp;
						}
						if(colHeaderFlag[j] != true){
							colHeaderFlag[j] = true;
							colHeader[j] = temp;
						}
						if(lastrowNode != null){
							lastrowNode.SetrowLink(temp);
						}
						if(lastcolNode[j] != null ){
							lastcolNode[j].SetcolLink(temp);
						}
						lastrowNode = temp;
						lastcolNode[j] = temp;
					}
				}
				lastrowNode = null;
			}
			break;
		default:
				
		}
	}
	
	public void Print(){
		int[][] nTemp = new int[nOrder][nOrder];
		for(int i = 0 ; i < nOrder ; i++){
			Arrays.fill(nTemp[i],0);
		}
		for(int i = 0 ; i < nOrder; i++){
			if(rowHeader[i] == null){
				Arrays.fill(nTemp[i], 0);
			}
			else{
				Node temp = rowHeader[i];
				//System.out.println(temp);
				while(temp != null){
					//System.out.println(temp);
					nTemp[temp.getRow()][temp.getCol()] = temp.getVal();
					temp = temp.getRowLink();
				}
			}
		}
		for(int i = 0 ; i < nOrder ; i++){
			for( int j = 0 ; j < nOrder ; j++){
				System.out.printf("%4d    ",nTemp[i][j]);
				//System.out.print(nTemp[i][j]);
				//System.out.print("     ");
			}
			System.out.println();
		}
	}
	
	public static Matrix Add(Matrix A, Matrix B) throws Exception{
		
		if( A.getNorder() != B.getNorder()){
			throw new Exception("Order mismatch for provided Matrix");
		}
		int order = A.getNorder();
		Matrix tempMatrix = new Matrix(order);
		
		Node tempANode = null;
		Node tempBNode = null;
		Node tempCNode = null;
		Node lastrowNode = null;
		Node[] lastcolNode = new Node[order];
		boolean[] rowHeaderFlag = new boolean[order];
		boolean[] colHeaderFlag = new boolean[order];
		Arrays.fill(rowHeaderFlag, false);
		Arrays.fill(colHeaderFlag, false);
		
		int nFlag = -1;
		
		for( int i = 0 ; i < order ; i++ ){
			tempANode = A.getrowHeader(i);
			tempBNode = B.getrowHeader(i);
			if( tempANode == null && tempBNode == null ){
				continue;
			}
			while( tempANode != null || tempBNode != null ){
				if( Node.IsSameMatrixPosition(tempANode, tempBNode)){
					tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal() + tempBNode.getVal());
					if( tempANode.getVal() + tempBNode.getVal() == 0 ){
						tempCNode = null;
					}
					nFlag = 0;
				}
				else {
					if(tempBNode == null ){
						tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal());
						nFlag = 1;
					}
					else if ( tempANode == null ){
						tempCNode = new Node(tempBNode.getRow(),tempBNode.getCol(),tempBNode.getVal());
						nFlag = 2;
					}
					else if( tempANode.getCol() < tempBNode.getCol()){
						tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal());
						nFlag = 1;
					}
					else {
						tempCNode = new Node(tempBNode.getRow(),tempBNode.getCol(),tempBNode.getVal());
						nFlag = 2;
					}
				}
				if( tempCNode != null ){
					if(rowHeaderFlag[i] != true){
						rowHeaderFlag[i] = true;
						tempMatrix.setrowHeader(i, tempCNode);
					}
					if(colHeaderFlag[tempCNode.getCol()] != true){
						colHeaderFlag[tempCNode.getCol()] = true;
						tempMatrix.setcolHeader(tempCNode.getCol(), tempCNode);
					}
					if(lastrowNode != null){
						lastrowNode.SetrowLink(tempCNode);
					}
					if(lastcolNode[tempCNode.getCol()] != null ){
						lastcolNode[tempCNode.getCol()].SetcolLink(tempCNode);
					}
					lastrowNode = tempCNode;
					lastcolNode[tempCNode.getCol()] = tempCNode;
				}
				
				
				
				if(nFlag != 2){
					tempANode = tempANode.getRowLink();
				}
				if(nFlag != 1){
					tempBNode = tempBNode.getRowLink();
				}
			}
		
			lastrowNode = null;
		}
		
		return tempMatrix;
	}
	public static Matrix Subtract(Matrix A, Matrix B) throws Exception{
		if( A.getNorder() != B.getNorder()){
			throw new Exception("Order mismatch for provided Matrix");
		}
		int order = A.getNorder();
		Matrix tempMatrix = new Matrix(order);
		Node tempANode = null;
		Node tempBNode = null;
		Node tempCNode = null;
		Node lastrowNode = null;
		Node[] lastcolNode = new Node[order];
		boolean[] rowHeaderFlag = new boolean[order];
		boolean[] colHeaderFlag = new boolean[order];
		Arrays.fill(rowHeaderFlag, false);
		Arrays.fill(colHeaderFlag, false);
		int nFlag = -1;
		for( int i = 0 ; i < order ; i++ ){
			tempANode = A.getrowHeader(i);
			tempBNode = B.getrowHeader(i);
			if( tempANode == null && tempBNode == null ){
				continue;
			}
			while( tempANode != null || tempBNode != null ){
				if( Node.IsSameMatrixPosition(tempANode, tempBNode)){
					tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal() - tempBNode.getVal());
					if( tempANode.getVal() - tempBNode.getVal() == 0 ){
						tempCNode = null;
					}
					nFlag = 0;
				}
				else {
					if(tempBNode == null ){
						tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal());
						nFlag = 1;
					}
					else if ( tempANode == null ){
						tempCNode = new Node(tempBNode.getRow(),tempBNode.getCol(),tempBNode.getVal() * -1);
						nFlag = 2;
					}
					else if( tempANode.getCol() < tempBNode.getCol()){
						tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal());
						nFlag = 1;
					}
					else {
						tempCNode = new Node(tempBNode.getRow(),tempBNode.getCol(),tempBNode.getVal() * -1);
						nFlag = 2;
					}
				}
				if( tempCNode != null ){
					if(rowHeaderFlag[i] != true){
						rowHeaderFlag[i] = true;
						tempMatrix.setrowHeader(i, tempCNode);
					}
					if(colHeaderFlag[tempCNode.getCol()] != true){
						colHeaderFlag[tempCNode.getCol()] = true;
						tempMatrix.setcolHeader(tempCNode.getCol(), tempCNode);
					}
					if(lastrowNode != null){
						lastrowNode.SetrowLink(tempCNode);
					}
					if(lastcolNode[tempCNode.getCol()] != null ){
						lastcolNode[tempCNode.getCol()].SetcolLink(tempCNode);
					}
					lastrowNode = tempCNode;
					lastcolNode[tempCNode.getCol()] = tempCNode;
				}
				
				
				
				if(nFlag != 2){
					tempANode = tempANode.getRowLink();
				}
				if(nFlag != 1){
					tempBNode = tempBNode.getRowLink();
				}
			}
		
			lastrowNode = null;
		}
		
		return tempMatrix;
	}
	public static Matrix ScalarMultiply(Matrix A, int nMul){
		int order = A.getNorder();
		Matrix tempMatrix = new Matrix(order);
		Node tempANode = null;
		Node tempCNode = null;
		Node lastrowNode = null;
		Node[] lastcolNode = new Node[order];
		boolean[] rowHeaderFlag = new boolean[order];
		boolean[] colHeaderFlag = new boolean[order];
		Arrays.fill(rowHeaderFlag, false);
		Arrays.fill(colHeaderFlag, false);
		
		int nFlag = -1;
		
		for( int i = 0 ; i < order ; i++ ){
			tempANode = A.getrowHeader(i);
			if( tempANode == null ){
				continue;
			}
			while( tempANode != null ){
				tempCNode = new Node(tempANode.getRow(),tempANode.getCol(),tempANode.getVal() * nMul);
				
				//System.out.println(tempCNode);
				if(rowHeaderFlag[i] != true){
					rowHeaderFlag[i] = true;
					tempMatrix.setrowHeader(i, tempCNode);
				}
				if(colHeaderFlag[i] != true){
					colHeaderFlag[i] = true;
					tempMatrix.setcolHeader(i, tempCNode);
				}
				if(lastrowNode != null){
					lastrowNode.SetrowLink(tempCNode);
				}
				if(lastcolNode[i] != null ){
					lastcolNode[i].SetcolLink(tempCNode);
				}
				lastrowNode = tempCNode;
				lastcolNode[i] = tempCNode;
				tempANode = tempANode.getRowLink();

			}
		
			lastrowNode = null;
		}
		
		return tempMatrix;
	}
	public static Matrix MatrixMultiply(Matrix A, Matrix B){
		if( A.getNorder() != B.getNorder()){
			return null;
		}
		int tempOrder = A.getNorder();
		Matrix tempMatrix = new Matrix(tempOrder);
		Node tempANode = null;
		Node tempBNode = null;
		Node tempCNode = null;
		Node lastrowNode = null;
		Node[] lastcolNode = new Node[tempOrder];
		boolean[] rowHeaderFlag = new boolean[tempOrder];
		boolean[] colHeaderFlag = new boolean[tempOrder];
		Arrays.fill(rowHeaderFlag, false);
		Arrays.fill(colHeaderFlag, false);
		
		for( int i = 0 ; i < A.rowHeader.length ; i++){
			for( int j = 0 ; j < B.colHeader.length ; j++){
				int val = 0;
				tempANode = A.getrowHeader(i);
				tempBNode = B.getcolHeader(j);
				if( tempANode == null || tempBNode == null){
					continue;
				}
				while( tempANode != null && tempBNode != null ){
					if( tempBNode.getRow() == tempANode.getCol()){
						val += tempANode.getVal() * tempBNode.getVal();
						tempANode = tempANode.getRowLink();
						tempBNode = tempBNode.getColLink();
					}
					else if ( tempBNode.getRow() < tempANode.getCol()){
						tempBNode = tempBNode.getColLink();
					}
					else {
						tempANode = tempANode.getRowLink();
					}
				}
				tempCNode = new Node( i,j,val);
				//System.out.println(tempCNode);
				if(rowHeaderFlag[i] != true){
					rowHeaderFlag[i] = true;
					tempMatrix.setrowHeader(i, tempCNode);
				}
				if(colHeaderFlag[j] != true){
					colHeaderFlag[j] = true;
					tempMatrix.setcolHeader(j, tempCNode);
				}
				if(lastrowNode != null){
					lastrowNode.SetrowLink(tempCNode);
				}
				if(lastcolNode[j] != null ){
					lastcolNode[j].SetcolLink(tempCNode);
				}
				lastrowNode = tempCNode;
				lastcolNode[j] = tempCNode;
			}
			lastrowNode = null;
		}
		return tempMatrix;
	}
	public static Matrix Power(Matrix A, int nPower){
		Matrix tempMatrix = Matrix.MatrixMultiply(A, A);
		for( int i = 1 ; i < nPower-1 ; i++){
			tempMatrix = Matrix.MatrixMultiply(tempMatrix, A);
		}
		return tempMatrix;
	}
	public static Matrix Transpose(Matrix A){
		int tempOrder = A.nOrder;
		Matrix tempMatrix = Matrix.ScalarMultiply(A, 1);
		
		//System.out.println(tempMatrix.rowHeader.length);
		ArrayList<Node> nodeList = new ArrayList<Node>();
		for( int i = 0 ; i < tempMatrix.rowHeader.length; i++){
			Node tempNode = tempMatrix.getrowHeader(i);
			while(tempNode != null){
				if( !nodeList.contains(tempNode)){
					nodeList.add(tempNode);
				}
				tempNode = tempNode.getRowLink();
			}
			
		}
		
		for( int i = 0 ; i < nodeList.size(); i++){
			Node tempNode = nodeList.get(i);
			tempNode.Transpose();
		}
		
		Node[] tempList = new Node[tempOrder];
		for( int i = 0 ; i < tempMatrix.rowHeader.length; i++){
			tempList[i] = tempMatrix.getrowHeader(i);
			tempMatrix.setrowHeader(i, tempMatrix.getcolHeader(i));
		}
		for(int i = 0 ; i < tempMatrix.colHeader.length; i++){
			tempMatrix.setcolHeader(i, tempList[i]);
		}
		return tempMatrix;
	}
}
public class App {

	public static void main(String[] args) throws Exception {
		System.out.println("Step 1: ");
		Matrix A = new Matrix();
		A.InitializeByInput(1);
		System.out.println("A: ");
		A.Print();
		
		Matrix B = new Matrix(4);
		B.InitializeByFormula(1);
		System.out.println("B: ");
		B.Print();
		
		Matrix C = new Matrix(4);
		C.InitializeByFormula(2);
		System.out.println("C: ");
		C.Print();
		
		Matrix D = new Matrix(4);
		D.InitializeByFormula(3);
		System.out.println("D: ");
		D.Print();
		
		
		System.out.println("User Matrix: ");
		Matrix userMatrix = new Matrix();
		userMatrix.InitializeByInput(0);
		userMatrix.Print();
		
		System.out.println("Step 2: ");
		Matrix E = Matrix.Add(B,D);
		System.out.println("E=B+D ");
		E.Print();
		
		Matrix F = Matrix.Subtract(D, C);
		System.out.println("F=D-C ");
		F.Print();
		
		Matrix G = Matrix.Add(A, B);
		System.out.println("G=A+B ");
		G.Print();
		
		Matrix H = Matrix.Subtract(A, B);
		System.out.println("H=A-B");
		H.Print();
		
		Matrix I = Matrix.Subtract(E, F);
		System.out.println("I=E-F");
		I.Print();
		
		Matrix J = Matrix.Add(G, H);
		System.out.println("J=G+H");
		J.Print();
		
		Matrix K = Matrix.ScalarMultiply(B, 5);
		System.out.println("K=5*B");
		K.Print();
		
		Matrix L = Matrix.ScalarMultiply(C, 8);
		System.out.println("L=8*C");
		L.Print();
		
		Matrix M = Matrix.ScalarMultiply(G, 3);
		System.out.println("M=3*G");
		M.Print();
		
		Matrix N = Matrix.ScalarMultiply(H, 2);
		System.out.println("N=2*H");
		N.Print();
		
		Matrix O = Matrix.ScalarMultiply(M, 2);
		System.out.println("O=2*M");
		O.Print();
		
		Matrix P = Matrix.ScalarMultiply(F, 3);
		System.out.println("P=3*F");
		P.Print();
		
		System.out.println("Step 3: ");
		Matrix Q = Matrix.MatrixMultiply(A, B);
		System.out.println("Q = A * B");
		Q.Print();
		
		Matrix R = Matrix.MatrixMultiply(B, D);
		System.out.println("R = B * D ");
		R.Print();
		
		Matrix S = Matrix.MatrixMultiply(E, G);
		System.out.println("S= E * G ");
		S.Print();
		
		Matrix T = Matrix.MatrixMultiply(G, E);
		System.out.println("T = G * E ");
		T.Print();
		
		Matrix U = Matrix.MatrixMultiply(Q, H);
		System.out.println("U = Q * H ");
		U.Print();
		
		Matrix V = Matrix.MatrixMultiply(S, T);
		System.out.println("V = S * T ");
		V.Print();
		
		
		Matrix W = Matrix.MatrixMultiply(R, S);
		System.out.println("W = R * S");
		W.Print();
		
		Matrix X = Matrix.Power(D, 5);
		System.out.println("X = D ^ 5 ");
		X.Print();
		
		Matrix Y = Matrix.Power(C, 8);
		System.out.println("Y = C ^ 8 ");
		Y.Print();
		
		Matrix Z = Matrix.Power(B, 10);
		System.out.println("Z = B ^ 10 ");
		Z.Print();
		
		Matrix AA = Matrix.Power(F, 2);
		System.out.println("AA = F ^ 2 ");
		AA.Print();
		
		Matrix AB = Matrix.Power(G, 3);
		System.out.println("AB = G ^ 3 ");
		AB.Print();
		
		Matrix AC = Matrix.Power(A, 4);
		System.out.println("AC = A ^ 4 ");
		AC.Print();
		
		Matrix AD = Matrix.Power(E, 3);
		System.out.println("AD = E ^ 3 ");
		AD.Print();
		
		Matrix AE = Matrix.Transpose(F);
		System.out.println("AE = transpose ( F ) ");
		AE.Print();
		
		Matrix AF = Matrix.Transpose(E);
		System.out.println("AF = transpose ( E ) ");
		AF.Print();
		
		Matrix AG = Matrix.Transpose(V);
		System.out.println("AG = transpose ( V ) ");
		AG.Print();
		
		Matrix AH = Matrix.Transpose(L);
		System.out.println("AH = transpose ( L ) ");
		AH.Print();
		
		Matrix AI = Matrix.Subtract(Matrix.Subtract(Matrix.Transpose(Matrix.Add(A, B)), Matrix.Transpose(A)), Matrix.Transpose(B));
		System.out.println("AJ = transpose(A+B) - transpose(A) - transpose(B) ");
		AI.Print();
		
		Matrix AJ = Matrix.Subtract(Matrix.MatrixMultiply(A, B),Matrix.MatrixMultiply(Matrix.Transpose(B), Matrix.Transpose(A)));
		System.out.println("AI = transpose(A*B) - (transpose(B)* transpose(A) ");
		AJ.Print();
	}

}
