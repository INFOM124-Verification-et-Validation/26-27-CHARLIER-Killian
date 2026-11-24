package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class BlinkyTest {
    PacManSprites sprites;
    GhostFactory ghostFactory;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostMapParser ghostMapParser;
    Player player;

    @Before
    public void setUp() {
        this.sprites = new PacManSprites();
        this.ghostFactory = new GhostFactory(sprites);
        this.levelFactory = new LevelFactory(sprites, ghostFactory);
        this.boardFactory = new BoardFactory(sprites);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = new PlayerFactory(sprites).createPacMan();
    }

    @Test
    public void testBlinkyNearOfPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#B    #   #");
        map.add("#    #    #");
        map.add("#P   #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testBlinkyFarFromPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#        B#");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.NORTH, Direction.WEST});
    }
    @Test
    public void testBlinkyNextToPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#PB   #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.WEST);
    }

    @Test
    public void testBlinkyNextToPlayerWithWalls() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#B  #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testBlinkyWithPlayerTrapped() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P#B  #   #");
        map.add("###  #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    @Test
    public void testBlinkyTrapped() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P   ##   #");
        map.add("#   #B#   #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    @Test
    public void testBlinkyChaseMode() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#        P#");
        map.add("#    B    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, currentLevel.getBoard());
        blinky.setMovement(4);

        Optional<Direction> aiDirection = blinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(Direction.NORTH);
    }
}
