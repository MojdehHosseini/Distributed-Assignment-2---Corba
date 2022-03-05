# CORBA_DAMS

# Steps to follow:
1. Generate client and server side bindings
> idlj -fall DAMS.idl

2. Compile all the files (Don't forget to add `lib` directory in your classpath)
> javac -classpath "./lib/*:./" *.java DAMSApp/*.java

3. Start orbd.
- To start orbd from a Solaris, Linux, or Mac OS X command shell, enter:
> orbd -ORBInitialPort 8050 -ORBInitialHost localhost&
- From an MS-DOS system prompt (Windows), enter:
> start orbd -ORBInitialPort 8050 -ORBInitialHost localhost

4. Run Server
> java -classpath "./lib/*:./" DAMSServer
- (The above command is specific to this project only)

or

> java -classpath "./lib/*:./" DAMSServer -ORBInitialPort 8050 -ORBInitialHost localhost

5. Run Client
> java -classpath "./lib/*:./" DAMSClient
(The above command is specific to this project only)

or

> java -classpath "./lib/*:./" DAMSClient -ORBInitialPort 8050 -ORBInitialHost localhost

# Note
- Don't forget to add jars inside 'lib' directory to your build path in your IDE.
- Download Binary version for CORBA from https://www.jacorb.org/download.html and use jar files inside the `lib` directory.

# Reference
[1] https://docs.oracle.com/javase/7/docs/technotes/tools/share/idlj.html
[2] https://docs.oracle.com/javase/8/docs/technotes/guides/idl/tutorial/GScompile.html
[3] Download Binary version of CORBA from https://www.jacorb.org/download.html and get


