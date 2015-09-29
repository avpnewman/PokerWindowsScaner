import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by podshibiakin on 14.08.2015.
 */
public class Main {
    static int a = 5;
    static int b = 6;

    public static void main(String... args) throws IOException {
        // инициализирую переменную в и помещаю туда файл
        File f = new File("src/images/Star (8).png");
        BufferedImage pokerScreen = null;
        // создаю экземляр класса для получения значению rgb
        getOneColour p = new getOneColour();
        // пробую прочитать файл
        try {
            pokerScreen = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // определяю разрешение прочитанной картинки
        int weightscreen = pokerScreen.getWidth();
        int heightscreen = pokerScreen.getHeight();

        pokerScreen = LetTurnItGrey(pokerScreen, p, weightscreen, heightscreen);

        int j, k, dot = 0, g = 0, nicknamesum =0, nicknamesumelse = 0;
        int crop1 = 0, crop2 = 0, crop3 = 0, crop4 = 0;
        for (j = 0; j < weightscreen - 1; j++) {
            for (k = 0; k < heightscreen - 1; k++) {
                if (p.getRRGB(pokerScreen.getRGB(j, k)) == 0 && p.getRRGB(pokerScreen.getRGB(j + 1, k)) == 200 && p.getRRGB(pokerScreen.getRGB(j, k + 1)) == 200 && p.getRRGB(pokerScreen.getRGB(j - 1, k - 1)) == 255 && p.getRRGB(pokerScreen.getRGB(j + 1, k + 1)) == 0) {
                    System.out.println("found black dot here: weight " + j + " and height " + k + " count " + dot);
                    int countdot = 0;
//считаю черные точки в обалсти креста

                    for (int dd = j; dd < j + 10; dd++) {
                        for (int mm = k; mm < k + 10; mm++) {
                            if (p.getRRGB(pokerScreen.getRGB(dd, mm)) == 0) {
                                countdot++;
                            }
                        }
                    }

                    //                   если похоже на крест то определяю область окна

                    if (countdot > 18 && countdot < 22) {
                        dot++;
                        System.out.println("Window here!" + dot);
// смещаю курсор за границы креста
                        j = j + 15;
// это похоже на движение курсора по горизонтали в поиске правого края окна
                        do {
                            j++;
                        } while (p.getRRGB(pokerScreen.getRGB(j, k)) == 255);
// выход цикла не белый поэтому минус 1
                        j = j - 1;
                        crop3 = j;
// а это похоже на движение от правого края окна вверх для определения верхней границы окна
                        do {
                            k--;
                        } while (p.getRRGB(pokerScreen.getRGB(j, k)) == 255);
                        k = k + 1;
                        crop2 = k;
// это уже движение от правой границы окна к левой по верхней кромке окна
                        do {
                            j--;
                        } while (p.getRRGB(pokerScreen.getRGB(j, crop2)) == 255);

                        j = j + 1;
                        crop1 = j;
// а это просто чудо код я пытаюсь определить где будет правая нижняя граница окна, полагаясь на то что она пропорциональна
                        crop4 = k + 979;
                        System.out.println("Window coordinates " + crop1 + " " + crop2 + " " + crop3 + " " + crop4);
// не забываю перенести курсор в правый верхний край окна,ищу следующее окно справа, слева искать не будет
                        j = crop3;
                        k = crop2;
                    }
                }
            }
        }
// делаем обрезание )) долбанные окна допустим рамер статический 1320 * 940
        pokerScreen = pokerScreen.getSubimage(crop1, crop2,1320, 940);

        System.out.println("Window coordinates for cut " + crop1 + " " + crop2 + " " + crop3 + " " + crop4);
        weightscreen = 1336;
        heightscreen = 979;
        //        *********************************************
        // определяю свой логин здесь статику надо заменить пропорциями
        for (j = 565; j < 695; j++){
            for (k = 680; k < 700; k++){
                if ((p.getRRGB(pokerScreen.getRGB(j,k))) == 0){
                    nicknamesum++;
                } else {
                    nicknamesumelse++;
                }
            }
        }
        System.out.println("200 dots sum = "+nicknamesum);





        //передаю параметры и рисунок в класс обработчик
        ScreenCopyWriter screenCopyWriter = new ScreenCopyWriter("django", weightscreen, heightscreen, pokerScreen);
////        //показываю окно
        screenCopyWriter.show();


    }

    //преобразование картинки в R палитру -  5 оттенков серого + белый - наверно надо вынести в отдельную процедуру
    private static BufferedImage LetTurnItGrey(BufferedImage pokerScreen, getOneColour p, int weightscreen, int heightscreen) {
        int j, k;
        for (j = 0; j < weightscreen; j++) {
            for (k = 0; k < heightscreen; k++) {
                int r = p.getRRGB(pokerScreen.getRGB(j, k));
                if (r < 50) {
                    r = 0;
                } else {
                    if (r < 100) {
                        r = 50;
                    } else {
                        if (r < 150) {
                            r = 100;
                        } else {
                            if (r < 200) {
                                r = 150;
                            } else {
                                if (r < 255) {
                                    r = 200;
                                }
                            }

                        }
                    }
                }

                Color m = new Color(r, r, r);
                pokerScreen.setRGB(j, k, m.getRGB());
            }
        }
        return pokerScreen;
    }
}
