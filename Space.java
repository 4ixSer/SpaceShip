import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by qny4i on 11.01.2017.
 */
public class Space {
    public static void main(String[] args) {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 16));
        game.run();
    }

    private int width;
    private int height;
    private SpaceShip ship;
    private ArrayList<Ufo> ufos = new ArrayList<Ufo>();
    private ArrayList<Rocket> rockets= new ArrayList<Rocket>();
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    public static Space game;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpaceShip getShip() {
        return ship;
    }

    public ArrayList<Ufo> getUfos() {
        return ufos;
    }

    public ArrayList<Rocket> getRockets() {
        return rockets;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }
    //управляет всей логикой игры
    public void run(){
        //Создаем холст для отрисовки.
        Canvas canvas = new Canvas(width, height);

        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //Игра работает, пока корабль жив
        while (ship.isAlive())
        {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents())
            {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если "стрелка влево" - сдвинуть фигурку влево
                System.out.print(event.getKeyCode());
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    ship.moveLeft();
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    ship.moveRight();
                    //Если "пробел" - запускаем шарик
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ship.fire();
            }

            //двигаем все объекты игры
            moveAllItems();

            //проверяем столкновения
            checkBombs();
            checkRockets();
            //удаляем умершие объекты из списков
            removeDead();

            //Создаем НЛО (1 раз в 10 ходов)
            createUfo();

            //Отрисовываем все объекты на холст, а холст выводим на экран
            canvas.clear();
            draw(canvas);
            canvas.print();

            //Пауза 300 миллисекунд
            Space.sleep(300);
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }
    //отрисовка очередного "кадра"
    public void draw(Canvas canvas){
        //draw game
        for (int i = 0; i < width + 2; i++)
        {
            for (int j = 0; j < height + 2; j++)
            {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++)
        {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++)
        {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }

        for (BaseObject object : getAllItems())
        {
            object.draw(canvas);
        }
    }
    public static void sleep(int delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
        }
    }

    public ArrayList<BaseObject> getAllItems(){
        ArrayList<BaseObject> baseObjects = new ArrayList<>();
        baseObjects.addAll(ufos);
        baseObjects.addAll(rockets);
        baseObjects.addAll(bombs);
        baseObjects.add(ship);

        return baseObjects;
    }

    public void moveAllItems(){

        for (BaseObject items: getAllItems()  ) {
            items.move();
        }

    }

    public void createUfo(){
        if (ufos.size() > 0) return;

        int random10 = (int) (Math.random() * 10);
        if (random10 == 0)
        {
            double x = width/2;
            double y = 0;
            ufos.add(new Ufo(x, y));
        }
    }

    /**
     * Проверяем бомбы.
     * а) столкновение с кораблем (бомба и корабль умирают)
     * б) падение ниже края игрового поля (бомба умирает)
     */
    public void checkBombs()
    {
        for (Bomb bomb : bombs)
        {
            if (ship.isIntersec(bomb))
            {
                ship.die();
                bomb.die();
            }

            if (bomb.getY() >= height)
                bomb.die();
        }
    }

    /**
     * Проверяем рокеты.
     * а) столкновение с НЛО (ракета и НЛО умирают)
     * б) вылет выше края игрового поля (ракета умирает)
     */
    public void checkRockets()
    {
        for (Rocket rocket : rockets)
        {
            for (Ufo ufo : ufos)
            {
                if (ufo.isIntersec(rocket))
                {
                    ufo.die();
                    rocket.die();
                }
            }

            if (rocket.getY() <= 0)
                rocket.die();
        }
    }

    /**
     * Удаляем умерсшие объекты (бомбы, ракеты, НЛО) из списков
     */
    public void removeDead()
    {
        for (BaseObject object : new ArrayList<BaseObject>(bombs))
        {
            if (!object.isAlive())
                bombs.remove(object);
        }

        for (BaseObject object : new ArrayList<BaseObject>(rockets))
        {
            if (!object.isAlive())
                rockets.remove(object);
        }

        for (BaseObject object : new ArrayList<BaseObject>(ufos))
        {
            if (!object.isAlive())
                ufos.remove(object);
        }
    }

}
