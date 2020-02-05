package application;

public class Main{
	public static void main(String[] args){
		Index i = new Index();
		IndexInverse iinv=new IndexInverse();
		Crawler c=new Crawler(i,iinv);	
	}
}
