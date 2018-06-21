import java.awt.*;
import java.io.*;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class Downloader {
    static File store = new File("store.txt");

    public static void main(String[] args) throws Exception {
        if(args.length != 2)
            System.exit(-1);
        String dist = args[0];
        String directory = args[1];
        if(dist.contains(".txt"))
            saveImage(new File(dist), directory);
        else
            saveImage(dist, directory);
    }

    public static void saveImage(String imageUrl, String directory) throws IOException {
        URL url = new URL(imageUrl);

        //формируем имя файла
        StringBuilder nameFile = new StringBuilder();
        for (int i = imageUrl.length() - 1; i >= 0; i--) {
            char c = imageUrl.charAt(i);
            if (c == '/')
                break;
            else
                nameFile.insert(0, c);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        long start = System.currentTimeMillis();

        Console c = System.console();
        while ((connection.getResponseMessage()).equals("Unauthorized")) {
            connection.disconnect();
            connection = (HttpURLConnection) url.openConnection();
            c.printf("Введите имя пользователя: \n");
            String username = c.readLine();
            c.printf("Введите пароль: \n");
            String password = String.copyValueOf(c.readPassword());
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
            connection.setRequestProperty ("Authorization", basicAuth);
        }

        connection.disconnect();
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        InputStream input = connection.getInputStream();
        FileWriter os = new FileWriter(store, true); //для истории

        c.printf("Изображение начинает скачиваться...\n");

        //усешные коды  - 2xx
        if ((connection.getResponseCode())/100 == 2) {
            try (OutputStream out = new FileOutputStream(directory + nameFile)) {
                //запись в файл
                if (connection.getResponseCode() == 200) {
                    byte[] b = new byte[2048];
                    int length;
                    int size = 0;
                    while ((length = input.read(b)) != -1) {
                        size = size + length;
                        out.write(b, 0, length);
                    }
                    //записываем в файл историю
                    String str = "[" + nameFile + "]" + "<" + url + ">" + "[изображение загружено]" + "\n";
                    String str2 = "[статус: " + connection.getResponseMessage() + "]" + "[http код: " + connection.getResponseCode() + "]" + "\n";
                    os.append(str, 0, str.length());
                    os.append(str2, 0, str2.length());

                    long finish = System.currentTimeMillis();
                    long time = finish - start;

                    //выводим в консоль
                    c.printf("Загрузка изображения окончена.\n");
                    String v = "[" + nameFile + "]" + "<" + url + ">" + "[размер: " + size + " байт]" + "[время: " + time + " мс]\n";
                    c.printf(v);
                    input.close();
                    os.close();
                }

            } catch (IOException e) {
                c.printf("Данной директории не существует или файл не может быть созда.\n");
            }
        }
        else {
            String str = "[" + nameFile + "]" + "<" + url + ">" + "[ошибка загрузки]" + "\n";
            String str2 = "[cтатус: " + connection.getResponseMessage() + "]" + "[http код: " + connection.getResponseCode() + "]" + "\n";
            os.append(str, 0, str.length());
            os.append(str2, 0, str2.length());
            os.close();
            c.printf("Файл не был загружен.\n");
        }

    }

    public static void saveImage(File fileImages, String directory) {
        Console c = System.console();
        try {
            FileInputStream f = new FileInputStream(fileImages);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String strLine;
            while ((strLine = br.readLine()) != null)
                saveImage(strLine, directory);
        } catch (IOException e) {
            c.printf("Ошибка в чтении файла: %s \n", fileImages.toString());
        }
    }
}