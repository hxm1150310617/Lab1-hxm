import java.io.*;
import java.util.*;


interface lab
{
	 digraph createDirectedGraph(String filename);//��������ͼ done
	 void showDirectedGraph(digraph G);//չʾ����ͼ done
	 String queryBridgeWords(digraph G, String word1, String word2);// ��ѯ�ŽӴ�done
	 String generateNewText(digraph G, String inputText);//����bridge word�������ı� done
	 String calcShortestPath(digraph G, String word1, String word2);//������������֮������·�� done
	 String randomWalk(digraph G);//�������done
	}
class digraph implements lab
{
	public String[] refrence;//��ŵ��ʣ����±�Ϊ���ʽڵ���
	public int[][] list;//����ͼ�Ķ�ά����
	public static int[] ifvisited;//���ʱ������
	public static int times;//�������ʱ���ظ���������Ϊȫ�ֱ���
	public static int sign;//��ǰ�����λ��
	public static int least;//���·����Ȩֵ��
 	public digraph(int i)//����һ���ַ�������
	{
		this.refrence=new String[i];
		this.list=new int[i][i];
	}
	public static void refreshifvisited(int i)//ˢ�·������飬��ʹ��֮ǰ����һ��
	{
		ifvisited=new int[i];
	}
	public static void refreshtimes()//�����ظ���������Ҫʱ����
	{times=0;}
	public static void refreshleast()//�������·����
	{least=0;}
    public int GetLength()//�õ��������Ч����
	{
		for(int i=0;i<this.refrence.length;i++)
		{
			if(this.refrence[i]==null) return i;
		}
		return 0;
	}
	public boolean IfHaveChild(String str)//�Ƿ�������ڱߣ����ڷ���true
	{
		for (int i=0;i<this.GetLength();i++)
		{
			if(this.list[this.GetNum(str)][i]>0) return true;
		}
		return false;
	}
	public int GetNum(String str)//ͨ���ַ������Ҷ�Ӧ�ı��ֵ
	{
		for(int j=0;j<this.GetLength();j++)
		{   if(this.refrence[j]==null) return -1;
			if(this.refrence[j].equals(str)==true) return j;
		}
		return -1;
	}
	public void Add(int i,int j)//����Ӧ��������ŵ�Ȩֵ+1����ʾi->j��һ�������
	{
		this.list[i][j]=this.list[i][j]+1;
	}
	public digraph createDirectedGraph(String filename)//��������ͼ
	{   
       try//���ں�����ļ�����
       {
   		   File file=new File(filename);//�ļ���
           if (!file.exists())//�����Ƿ����
           {System.err.println("Not Fing the File!\n");}
           
    	    String line;
			String reg = "\\s+";
			String E ="[a-zA-Z]";
			String word,preword=null;
			int index=0;
			
			BufferedReader bf = new BufferedReader(new FileReader(file));
			while((line = bf.readLine())!=null)  //һ��һ�ж�ȡ
			{
				for(int i=0;i<line.length();i++)
				{
					char ch = line.charAt(i);
					String ch1 = String.valueOf(ch);
					if(!ch1.matches(E))           //����Ӣ����ĸ�ַ��ÿո�ȡ��
						line=line.replace(ch,' ');
					
				}
				String []strs = line.split(reg);
				for(int i=0;i<strs.length;i++)
				{
					word = strs[i];
				    if(this.GetNum(word.toLowerCase().trim())==-1)//���û�д���������ʣ������
				    { 
				       this.refrence[index]=(word.toLowerCase().trim());
				        index++;
				    }//��Ч���ʴ���ο�������,ͬʱ��дת��
				  
			      if(preword!=null)//���ǰһ���ʲ�Ϊ�գ���ǰһ���ʵ��˵��ʵ�Ȩֵ+1
			      {  
			         
			    	  this.Add(this.GetNum(preword.toLowerCase().trim()),this.GetNum(word.toLowerCase().trim()) );
			      }
			      preword = word;
			      //System.out.println(preword);
				}
		  }
		 bf.close();  
       }
       catch (Exception e) {
    	   e.printStackTrace();
    	  }
       return this;  
	}
	public void printdigraph(digraph G)//�������
	{
		/*���������*/
		int index=G.GetLength();
		System.out.println(index);
		   for(int z=0;z<index;z++)
		   { System.out.print("\""+G.refrence[z]+"\""+' ');}
		     System.out.print('\n');
		     for (int x=0;x<index;x++)
		     {
			   for(int y=0;y<index;y++)
			   {
				   System.out.print(G.list[x][y]+" ");
			   }
			   System.out.println("\n");
		    }    		      		    
    }
	
	public String queryBridgeWords(digraph G, String word1, String word2)// ��ѯ�ŽӴ�
	{
		int index1=G.GetNum(word1);int index2=G.GetNum(word2);
		if(index1==-1||index2==-1) return "No in";//������һ���ʲ�����
		else if(G.list[index1][index2]>0) return "No have";//�����������ŽӴ�
		else
		{			
			ifvisited[index1]=1;ifvisited[index2]=1;
			for(int i=0;i<G.GetLength();i++)//�ж��Ƿ����ʼ����ŽӴ�
			{
				if(G.list[index1][i]>0)
				{					
						if(G.list[i][index2]>0&&ifvisited[i]==0)//����δ�����ʹ�
						{
							ifvisited[i]=1;
							return G.refrence[i];//�����Ч��
						}
				}
			}
		for(int j=0;j<G.GetLength();j++)//�ж��Ƿ���ֹ���Ч��
		{
			if(j==index1||j==index2);
			else if(ifvisited[j]==1) return "No more";//���ֹ�	
		}
		return "No have";//δ���ֹ�
		}
		
	}
	public String generateNewText(digraph G, String inputText)//����bridge word�������ı� 
	{
		String sentence[]=inputText.split("\\s+");//�ָ�������ַ���	
		String strout=new String();//����������ַ���
		String strbridge;//��ŵ�ǰ�ŽӴ�
		String strconnect;//�ó�����Ч�ŽӴ�
		for(int i=0;i<sentence.length-1;i++)
		{   refreshifvisited(G.GetLength());
			strout=strout+sentence[i]+" ";
			strbridge=G.queryBridgeWords(G, sentence[i], sentence[i+1]);
			if(strbridge.equals("No in")||strbridge.equals("No have")) strconnect=" ";//������ʼ����ŽӴʣ�����Ҫ�����µ���
			else //������ʼ����ŽӴʣ��������һ���ŽӴ�
			{
				String[] bridgewords=new String[G.GetLength()];
                int index=0;
                Random random=new Random();
				while(!strbridge.equals("No more"))//δ��ȫ�����ŽӴ�ʱ
				{
					bridgewords[index]=strbridge;
					index++;
					strbridge=G.queryBridgeWords(G, sentence[i], sentence[i+1]);
				}
				strconnect=bridgewords[random.nextInt(index)]+" ";
			}
			strout=strout+strconnect;
		}
		strout=strout+sentence[sentence.length-1];
		return strout;
		
	}
    public String randomWalk(digraph G)//�������
	{   int temp;
 		Random index=new Random();//����һ����Χ�ڵ������
		temp=index.nextInt(G.GetLength());
		if(IfHaveChild(G.refrence[sign])==false) return "end";//��ǰ�ڵ������ӽ�㣬�������߽���
		if(G.list[sign][temp]>0&&ifvisited[temp]==0)//��ǰ�ַ������ڽ��ַ�����δ������
		{
			ifvisited[temp]=1;//��Ƿ���
			//this.sign=temp;
			//this.refreshtimes();
			sign=temp;
			refreshtimes();
			return G.refrence[temp];//��ǰ�ַ�Ϊ������ַ������ص�ǰ�ַ�
		}                  //this.sign
		else if(G.list[sign][temp]>0&&ifvisited[temp]==1&&(times==0||times==1))//��ǰ�Ѿ����ʹ���δ�ظ���˵��ֻ�����ظ����ʣ�δ���ǳ����ظ����ӣ���������
		{
			ifvisited[temp]=1;//��Ƿ���
			sign=temp;
			times++;
			return refrence[temp];//��ǰ�ַ�Ϊ������ַ������ص�ǰ�ַ�
		}
		else if(G.list[sign][temp]>0&&ifvisited[temp]==1&&times==2)//��ǰ�Ѿ����ʹ��������ظ���˵�������ظ����ӣ����߽���
		{
			return "end";
		}
		else//��ǰ���������������һ���ʵ��±굫��ǰ�ߵ��Ľڵ㻹���ӽڵ㣬����ִ�к�����ֱ��������Ч�±�
		{
			return "continue";
		}
	}
    public void showDirectedGraph(digraph G)//չʾ����ͼ
    {
    	GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        int index=G.GetLength();
        for(int i=0;i<index;i++)
        {
        	for(int j=0;j<index;j++)
        	{
        		if(G.list[i][j]>0)
        		{
        		    gv.addln(G.refrence[i]+"->"+G.refrence[j]+"[label=\""+G.list[i][j]+"\"];");
        		   // gv.addln(G.refrence[i]+"->"+G.refrence[j]+"[label=\""+G.list[i][j]+"\",style=\"dashed\"];");
        		}
        	}
        }
        
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        
        String type = "gif";
        File out = new File("D:/graphviz2.38/workspace/out1."+type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }
    
    //չʾ���·��
    public void showShortestRoute(digraph G, String route)//չʾ����ͼ
    {
    	String []routes=route.split("->");
    	int flag;
    	GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        int index=G.GetLength();
        for(int i=0;i<index;i++)
        {
        	for(int j=0;j<index;j++)
        	{
        		if(G.list[i][j]>0)
        		{
        			//System.out.println(G.list[i][j]);
        		    
        		    flag=0;
        		    for(int k=0;k<routes.length-1;k++)
        		    {
        		    	if(G.refrence[i].equals(routes[k])&&G.refrence[j].equals(routes[k+1]))
        		    	{
        		    		 gv.addln(G.refrence[i]+"->"+G.refrence[j]+"[label=\""+G.list[i][j]+"\",style=\"dashed\"];");
        		    		 flag=1;
        		    		 break;
        		    	}
        		    }
        		    if(flag==0)
        		    {
        		    	 gv.addln(G.refrence[i]+"->"+G.refrence[j]+"[label=\""+G.list[i][j]+"\"];");
        		    }
        		    
        		}
        	}
        }
        
        gv.addln(gv.end_graph());
     //   System.out.println(gv.getDotSource());
        
        String type = "gif";
        File out = new File("D:/graphviz2.38/workspace/out2."+type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }
    
    public String calcShortestPath(digraph G, String word1, String word2)//������������֮������·�� 
    {
    	int num=G.GetLength();
        int index1=G.GetNum(word1);int index2=G.GetNum(word2);
        if(index1==-1||index2==-1) return "No way";
    	   int[][] A=new int[num][num];
    	   int[][] path=new int [num][num];
    	   int i,j,k,n=num;
    	   for(i=0;i<n;i++)
    	   {
    		   for(j=0;j<n;j++)
    		   {   if(i==j) A[i][j]=0;
    		   else if(G.list[i][j]==0) A[i][j]=999;//��·��ʱȨֵ����Ϊ999 
    		   else A[i][j]=G.list[i][j];
    			   path[i][j]=-1;
    		   }
    	   }
    	  for(k=0;k<n;k++)
    	  {
    		  for(i=0;i<n;i++)
    		  {
    			  for(j=0;j<n;j++)
    			  {
    				  if(A[i][j]>(A[i][k]+A[k][j]))
    				  {
    					  A[i][j]=A[i][k]+A[k][j];
    					  path[i][j]=k;
    				  }
    			  }
    		  }
    	  }
    	  least=A[index1][index2];//�������·��ֵ
    	  String str;
    	  str=G.ReturnBetweenWords(path, index1, index2);
    	  return str;
    	  
    	  
    }
    public String ReturnBetweenWords(int[][] A,int index1,int index2)//�ݹ鷵���ַ�������������
    {
    	if(A[index1][index2]==-1) return this.refrence[index1]+"->";
    	else return this.ReturnBetweenWords(A, index1, A[index1][index2])+this.ReturnBetweenWords(A,A[index1][index2], index2);
    	}
}
public class Lab1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Scanner input=new Scanner(System.in);
        String filename=new String("D:\\graphviz2.38\\workspace\\test.txt");
        String str1,str2,str3;
        digraph nl=new digraph(100);//�����µ�����ͼ��,ͬʱ��ʼ����ά����
        nl=nl.createDirectedGraph(filename);//��������ͼ
        nl.printdigraph(nl);
        nl.showDirectedGraph(nl);
        /*��ѯ�ŽӴ�*/
        System.out.println("Please input the words you want to search: ");
        str1=input.next();
        input.nextLine();
        str2=input.next();
        input.nextLine();
        digraph.refreshifvisited(nl.GetLength());   
        str3=nl.queryBridgeWords(nl, str1, str2);       
        if(str3.equals("No in")) System.out.println("No \""+str1+"\" or \""+str2+"\" in the graph !");
        else if(str3.equals("No have")) System.out.println("No bridge words from \""+str1+"\" to \""+str2+"\" !");
        else 
        {
        	System.out.print("The bridge words from \""+str1+"\" to \""+str2+"\" are :"+str3);
        	 str3=nl.queryBridgeWords(nl, str1, str2);    
        	while(!str3.equals("No more")) 
        	{        		
        		String str4=new String(nl.queryBridgeWords(nl, str1, str2));        		
        		if(!str3.equals("No more")&&!str4.equals("No more")) 
        			{System.out.print(","+str3);str3=str4;}
        		else if(!str3.equals("No more")&&str4.equals("No more"))
        			{System.out.print(" and "+str3);str3=str4;}
        	}
        }
        System.out.println("\n**********************************************");
        /*�������*/
        System.out.println("Now begin the random waklind.");
        Random random=new Random();
        String result=new String();
        //String strin=new String("Yes");//�����ַ����ж��Ƿ��������
        digraph.sign=random.nextInt(nl.GetLength());//����һ����ʼ����λ��
        digraph.refreshifvisited(nl.GetLength());
        digraph.refreshtimes();
        digraph.ifvisited[digraph.sign]=1;//���Ϊ�ѷ���
        System.out.print(nl.refrence[digraph.sign]+" ");
        while(!result.equals("end"))//δ���ֽ�����־ʱ
        {    result=nl.randomWalk(nl);
        	if(!result.equals("continue")&&!result.equals("end")) 
        	{
        		System.out.print(result+" ");//���õ���Ч���ʱ������ַ�}
        		} }
       /* while(!result.equals("end")&&!strin.equals("No"))//δ���ֽ�����־ʱ
        {    result=nl.randomWalk(nl);
             if(!strin.equals("Yes")&&!strin.equals("No"))
             {
            	 System.out.println("Wrong inputs!");
            	 break;
             }        
        	if(!result.equals("continue")&&!result.equals("end")&&strin.equals("Yes")) 
        	{
        		System.out.print(result+" \n");//���õ���Ч���ʱ������ַ�}
        		System.out.println("Would you like to continue ?(inout Yes to continue, No to stop)");
        		strin=input.next();}        		             
        }*/
        System.out.println("\nComplete random walk!");
        System.out.println("**********************************************");
        /*����bridgewords�������ı�*/
        System.out.println("Please input the new text: ");
        String inputText=new String(input.nextLine());
        System.out.println(nl.generateNewText(nl, inputText));
        System.out.println("**********************************************");
        /*���·������*/
        System.out.println("Please input the words you want to search for the least cost: ");
        String str5,str6,str7;
        str5=input.next();
        str6=input.next();
        digraph.refreshleast();
        String strcheapestroute=new String();
        strcheapestroute=nl.calcShortestPath(nl, str5, str6);
        if(strcheapestroute.equals("No way"))//���·��������
        {
        	System.out.println("No route bewteen the words!");
        }
        else{
        System.out.println("The least cost way is :"+strcheapestroute+str6);
        nl.showShortestRoute(nl,strcheapestroute+str6);
        System.out.println("The least cost is: " +digraph.least);}
        System.out.println("**********************************************");
        
        //��Դ���·��
        System.out.println("Please input a word you want to search for the least cost: ");
        str7=input.next();
        int index=nl.GetNum(str7);
        for(int i=0;i<nl.GetLength();i++)
        {
        	if(i!=index)
        	{
        digraph.refreshleast();
        String strcheapestroute1=new String();
        strcheapestroute1=nl.calcShortestPath(nl, str7,nl.refrence[i]);
        if(strcheapestroute1.equals("No way"))//���·��������
        {
        	System.out.println("No route bewteen the words!");
        }
        else{
        System.out.println("The least cost way is :"+strcheapestroute1+nl.refrence[i]);
       // nl.showShortestRoute(nl,strcheapestroute1+str6);
        System.out.println("The least cost is: " +digraph.least);}
        System.out.println("**********************************************");
        	}
        }
        input.close();
     //   System.out.println("\nHello World!");
     

	}

}
