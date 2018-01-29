import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;




public class Test {
	// public static void main(String[] args) {
	//
	// long startTime=System.currentTimeMillis(); //获取开始时间
	//
	// for (int i = 0; i <10; i++) {
	// System.out.println(UUID.randomUUID().toString());
	// }
	//
	//
	// long endTime=System.currentTimeMillis(); //获取结束时间
	//
	// System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	// }

	// public static void main(String args[]) {
	// // try {}catch(Exception e){
	// //　　　　　　//出错，打印出错信息
	// //　　　　}
	// 　　}
	
	
	
	
	
	private static Integer roleLevel;
	public static void main(String[] args) {
		
		
		String str ="Wed Jan 03 17:20:57 CST 2018";
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date(str);
		d.parse(str);
		System.out.println(sf.format(d));
//		try {
//			//Date sdatr =sf.parse(str);
//			
//			System.out.println(sf.format(d));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		int a =3;
		int b=7;
		
		
		a = a^b;
		
		b = a^b;
		
		a = a^b;
		
//		a =(a^b)^a;
//		
//		b =(b^a)^b;
		
//		System.out.println(10&8);
//		System.out.println(a );
		
		/*
		 * Collection -> List -> Vector()
		 * Collection -> List -> ArrayList()
		 * Collection -> List -> LinkedList()
		 * Collection -> Set  ->
		 * Collection -> Set  ->
		 * Collection -> Set  ->
		 * 
		 * 
		 * 
		 * 
		 *
		 **/
		
		Collection<Object> c = new ArrayList();
		
		
		c.add("collectionlinkedlist");
		c.add("collection");
		
		Iterator<Object> aas = c.iterator();
		System.out.println(c.toString());
		while(aas.hasNext()){
			
			System.out.println(aas.next());
		}
		
		
		
//		Map<String, String> a = new HashMap<String, String>();
//		
//		
//		Set<Map.Entry<String, String>> s = a.entrySet();
//		
//		Iterator<Entry<String, String>> it = s.iterator();
//		
//		while(it.hasNext()){
//			Entry<String,String> en = it.next();
//			en.getKey();
//			
//		}
		
		
		
//		a.put("dd", "dd");
//		 
//		String uuid= UUID.randomUUID().toString();
//		
////		
////	String a ="<div></div>";
////	a = a.replaceAll("<", "#");
//	System.out.println(a.get("dd"));
		
//		String str="%E6%B3%95%E5%9B%BD%E5%9C%B0%E6%96%B9%3D44";
//		
//		try {
//			System.out.println(java.net.URLDecoder.decode(str,"utf-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		try {
//
//			ServerSocket server = null;
//			try {
//				server = new ServerSocket(8888);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			Socket socket = null;
//			
//			
//			while(true){
//				try {
//					socket = server.accept();
//					// 使用accept()阻塞等待客户请求，有客户
//					// 请求到来则产生一个Socket对象，并继续执行
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				String line;
//				BufferedReader is = new BufferedReader(new InputStreamReader(
//						socket.getInputStream()));
//				// 由Socket对象得到输入流，并构造相应的BufferedReader对象
//				PrintWriter os = new PrintWriter(socket.getOutputStream());
//				// 由Socket对象得到输出流，并构造PrintWriter对象
//				BufferedReader sin = new BufferedReader(new InputStreamReader(
//						System.in));
//				// 由系统标准输入设备构造BufferedReader对象
//				System.out.println("Client:" + is.readLine());
//				// 在标准输出上打印从客户端读入的字符串
//				line = sin.readLine();
//				// 从标准输入读入一字符串
//				while (!line.equals("bye")) {
//					// 如果该字符串为 "bye"，则停止循环
//					os.println(line);
//					// 向客户端输出该字符串
//					os.flush();
//					// 刷新输出流，使Client马上收到该字符串
//					System.out.println("Server:" + line);
//					// 在系统标准输出上打印读入的字符串
//					System.out.println("Client:" + is.readLine());
//					// 从Client读入一字符串，并打印到标准输出上
//					line = sin.readLine();
//					// 从系统标准输入读入一字符串
//				} // 继续循环
//				os.close(); // 关闭Socket输出流
//				is.close(); // 关闭Socket输入流
//			}
//			
////			socket.close(); // 关闭Socket
////			server.close(); // 关闭ServerSocket
//
//		} catch (Exception e) {
//		}

		// try {System.out.println();}catch(Exception e ){
		// 　　　　
		// 　　　}
	}
}