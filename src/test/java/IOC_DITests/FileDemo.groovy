package IOC_DITests

class FileDemo {

	static main(agrs){
		URL url=FileDemo.getResource("/demo.properties")
		println url
	}
}
