class SecretHandler {

   IndexHandler (String name) {
   }

   public void run() {
    out.write("HTTP/1.1 301 Moved Permanently\r\n");
    out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
    out.write('Location: http://www.example.com/');
    out.write("Server: Apache/0.8.4\r\n");
    out.write("Content-Type: text/html\r\n");
    out.write("Content-Length: 59\r\n");
    out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
    out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
    out.write("\r\n");
    out.write("<TITLE>Hola</TITLE>");
    out.write("<P>Pagina bienvenida!</P>");
    out.write("\n\n");
   }
}