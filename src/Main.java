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
        // ������������� ���������� � � ������� ���� ����
        File f = new File("src/images/Star (8).png");
        BufferedImage pokerScreen = null;
        // ������ �������� ������ ��� ��������� �������� rgb
        getOneColour p = new getOneColour();
        // ������ ��������� ����
        try {
            pokerScreen = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ��������� ���������� ����������� ��������
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
//������ ������ ����� � ������� ������

                    for (int dd = j; dd < j + 10; dd++) {
                        for (int mm = k; mm < k + 10; mm++) {
                            if (p.getRRGB(pokerScreen.getRGB(dd, mm)) == 0) {
                                countdot++;
                            }
                        }
                    }

                    //                   ���� ������ �� ����� �� ��������� ������� ����

                    if (countdot > 18 && countdot < 22) {
                        dot++;
                        System.out.println("Window here!" + dot);
// ������ ������ �� ������� ������
                        j = j + 15;
// ��� ������ �� �������� ������� �� ����������� � ������ ������� ���� ����
                        do {
                            j++;
                        } while (p.getRRGB(pokerScreen.getRGB(j, k)) == 255);
// ����� ����� �� ����� ������� ����� 1
                        j = j - 1;
                        crop3 = j;
// � ��� ������ �� �������� �� ������� ���� ���� ����� ��� ����������� ������� ������� ����
                        do {
                            k--;
                        } while (p.getRRGB(pokerScreen.getRGB(j, k)) == 255);
                        k = k + 1;
                        crop2 = k;
// ��� ��� �������� �� ������ ������� ���� � ����� �� ������� ������ ����
                        do {
                            j--;
                        } while (p.getRRGB(pokerScreen.getRGB(j, crop2)) == 255);

                        j = j + 1;
                        crop1 = j;
// � ��� ������ ���� ��� � ������� ���������� ��� ����� ������ ������ ������� ����, ��������� �� �� ��� ��� ���������������
                        crop4 = k + 979;
                        System.out.println("Window coordinates " + crop1 + " " + crop2 + " " + crop3 + " " + crop4);
// �� ������� ��������� ������ � ������ ������� ���� ����,��� ��������� ���� ������, ����� ������ �� �����
                        j = crop3;
                        k = crop2;
                    }
                }
            }
        }
// ������ ��������� )) ��������� ���� �������� ����� ����������� 1320 * 940
        pokerScreen = pokerScreen.getSubimage(crop1, crop2,1320, 940);

        System.out.println("Window coordinates for cut " + crop1 + " " + crop2 + " " + crop3 + " " + crop4);
        weightscreen = 1336;
        heightscreen = 979;
        //        *********************************************
        // ��������� ���� ����� ����� ������� ���� �������� �����������
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





        //������� ��������� � ������� � ����� ����������
        ScreenCopyWriter screenCopyWriter = new ScreenCopyWriter("django", weightscreen, heightscreen, pokerScreen);
////        //��������� ����
        screenCopyWriter.show();


    }

    //�������������� �������� � R ������� -  5 �������� ������ + ����� - ������� ���� ������� � ��������� ���������
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
