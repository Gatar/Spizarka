# Spizarka

Android application for managing home pantry items like cans, pastas, medicines etc. 
App works on idea of scan barcode of each type of product, which will be store longer than few days, to get it on stock. When product is used, barcode is scanned second time to delete product from database.

<dl><br></dl>
Provides functionalities: <br>
* Adding, actualizing, changing quantity etc of each item
* View overview list of all items
* Detail view of each item
* Automatic creating shopping list

<dl><br></dl>
In progress:
* database in cloud communicated via REST, integrated with internal phone database (see in my other repositorium for webapi)
* UI for account and general settings
* provide connection to one database from many accounts/users with instant data actualization

<dl><br></dl>
Use barcode scanner: [Barcode Scanner based on ZXing](https://github.com/dm77/barcodescanner)
Webapi for database: [Spizarka Servlet] (https://github.com/Gatar/SpizarkaServlet) 