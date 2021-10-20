/*
 * The MIT License
 *
 * Copyright 2021 imkbe.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ProjetoCryptoverse;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author imkbe
 */
public class ConnectionBD {
    private BasicDataSource dataSource;

//  SQLServer  
    public ConnectionBD() {
        this.dataSource = new BasicDataSource();
        this.dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.dataSource.setUrl("jdbc:sqlserver://cryptoverse.database.windows.net:1433;database=CryptoVerse;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;");
        this.dataSource.setUsername("admcrypto");
        this.dataSource.setPassword("#Gfgrupo8");
    }
    
//    MYSQL
//    public ConnectionBD() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost/CryptoVerse?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//        dataSource.setUsername("CryptoVerse");
//        dataSource.setPassword("1234");
//    }
//    
    public BasicDataSource getDataSource(){
        return dataSource;
}
}
