/**
 * Created by qny4i on 11.01.2017.
 */
public class SpaceShip extends BaseObject {

    //картинка корабля для отрисовки
    private static int[][] matrix = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
    };

    public SpaceShip(double x, double y) {
        super(x, y, 3);
    }

    private double dx=0;

    void moveLeft(){
        dx=-1;
    }

    void moveRight(){
        dx=1;
    }


    @Override
    public void draw(Canvas canvas) {
            canvas.drawMatrix(x - radius + 1, y, matrix, 'M');
    }
    @Override
    public void move() {

         checkBorders(radius, Space.game.getWidth() - radius + 1, 1, Space.game.getHeight() + 1);
         x  += dx;
    }

    /*
    * Стреляем.
    * Создаем две ракеты: слева и справа от корабля.
      */
    public void fire()
    {
        Space.game.getRockets().add(new Rocket(x - 2, y));
        Space.game.getRockets().add(new Rocket(x + 2, y));
    }
}
