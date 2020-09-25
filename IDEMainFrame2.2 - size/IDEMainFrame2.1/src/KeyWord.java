import java.util.HashSet;
import java.util.Set;

public class KeyWord {
	
	static public Set<String> dataKeywords;
	static public Set<String> gramKeywords;
	static public Set<String> headers;
	static public Set<String> funcKeywords;
	
	static {
	   dataKeywords = new HashSet<String>();
		
	   //数据类型关键字12个
       dataKeywords.add("char");
       dataKeywords.add("double");
       dataKeywords.add("enum");
       dataKeywords.add("float");
       dataKeywords.add("int");
       dataKeywords.add("long");
       dataKeywords.add("short");
       dataKeywords.add("signed");
       dataKeywords.add("struct");
       dataKeywords.add("union");
       dataKeywords.add("unsigned");
       dataKeywords.add("void");
        
        //存储型关键字5个
       dataKeywords.add("auto");
       dataKeywords.add("extern");
       dataKeywords.add("register");
       dataKeywords.add("static");  
       dataKeywords.add("const");
       
       dataKeywords.add("bool");
       dataKeywords.add("char16_t");
       dataKeywords.add("char32_t");
       dataKeywords.add("wchar_t");
       dataKeywords.add("class");
       dataKeywords.add("compl");
       dataKeywords.add("constexpr");
       dataKeywords.add("const_cast");
       dataKeywords.add("nullptr");
       dataKeywords.add("private");
       dataKeywords.add("protected");
       dataKeywords.add("public");
       dataKeywords.add("private");
       dataKeywords.add("protected");
       dataKeywords.add("public");
       
       /*======================================================================*/
       
       gramKeywords = new HashSet<String>();
       
       gramKeywords.add("alignas");
       gramKeywords.add("alignof");
       gramKeywords.add("and");
       gramKeywords.add("and_eq");
       gramKeywords.add("asm");
       gramKeywords.add("bitand");
       gramKeywords.add("bitor");
       gramKeywords.add("break");
       gramKeywords.add("case");
       gramKeywords.add("catch");
       gramKeywords.add("continue");
       gramKeywords.add("decltype");
       gramKeywords.add("default");
       gramKeywords.add("delete");
       gramKeywords.add("do");
       gramKeywords.add("dynamic_cast");
       gramKeywords.add("else");
       gramKeywords.add("explicit");
       gramKeywords.add("export");
       gramKeywords.add("false");
       gramKeywords.add("for");
       gramKeywords.add("friend");
       gramKeywords.add("goto");
       gramKeywords.add("if");
       gramKeywords.add("inline");
       gramKeywords.add("mutable");
       gramKeywords.add("namespace");
       gramKeywords.add("new");
       gramKeywords.add("noexcept");
       gramKeywords.add("not");
       gramKeywords.add("not_eq");
       gramKeywords.add("operator");
       gramKeywords.add("or");
       gramKeywords.add("or_eq");
       gramKeywords.add("register");
       gramKeywords.add("reinterpret_cast");
       gramKeywords.add("return");
       gramKeywords.add("static_assert");
       gramKeywords.add("static_cast");
       gramKeywords.add("switch");
       gramKeywords.add("template");
       gramKeywords.add("this");
       gramKeywords.add("thread_local");
       gramKeywords.add("throw");
       gramKeywords.add("true");
       gramKeywords.add("try");
       gramKeywords.add("typedef");
       gramKeywords.add("typeid");
       gramKeywords.add("typename");
       gramKeywords.add("using");
       gramKeywords.add("virtual");
       gramKeywords.add("void");
       gramKeywords.add("volatile");
       gramKeywords.add("while");
       gramKeywords.add("xor");
       gramKeywords.add("xor_eq");
       
       /*======================================================================*/
       
       headers = new HashSet<String>();
       
       headers.add("include");
       headers.add("define");
       headers.add("ifndef");
       headers.add("endif");
       
       /*======================================================================*/
       
       funcKeywords = new HashSet<String>();
       
       funcKeywords.add("printf");
       funcKeywords.add("scanf");
       funcKeywords.add("main");
       funcKeywords.add("typeof");
       funcKeywords.add("sizeof");
	}
	
}
