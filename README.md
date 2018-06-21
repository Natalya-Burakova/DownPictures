# DownPictures 
Чтобы запустить jar-файл в консоли: java -jar burakova.nata-1.0-SNAPSHOT.jar URL|File directory

Пример (скачивание по URL):
java -jar burakova.nata-1.0-SNAPSHOT.jar http://komotoz.ru/kartinki/images/kartinki_pro_lubov/kartinki_pro_lubov_02.jpg pic//

Результат выполнения:

Изображение начинает скачиваться...
Загрузка изображения окончена.
[kartinki_pro_lubov_02.jpg]<http://komotoz.ru/kartinki/images/kartinki_pro_lubov/kartinki_pro_lubov_02.jpg>[размер: 84556 байт][время: 148 мс]


P.S:
В директории, которая была указана, появляется картинка/картинки, которую вы скачивали.
А в текущей директории, создается файл истории store.txt, который хранит всю информацию о скачивании картинок (удачном и неудачном).

В данном случае в него записалась строка: [kartinki_pro_lubov_02.jpg]<http://komotoz.ru/kartinki/images/kartinki_pro_lubov/kartinki_pro_lubov_02.jpg>[изображение загружено]
[статус: OK][http код: 200].

Пример (скачивание по ссылкам из файла):
java -jar burakova.nata-1.0-SNAPSHOT.jar file.txt pic//

Результат выполнения:

Изображение начинает скачиваться...
Загрузка изображения окончена.
[kartinki_s_nadpisjami_pro_kotov_01.jpg]<http://komotoz.ru/kartinki/images/kartinki_s_nadpisjami_pro_kotov/kartinki_s_nadpisjami_pro_kotov_01.jpg>[размер: 51380 байт][время: 144 мс]

Если для скачивания картинки необходимо пройти аутентификацию, программа попросит вас ввести логин и пароль.
