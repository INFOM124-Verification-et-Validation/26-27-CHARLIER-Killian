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

public class PinkyTest {
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
    public void testPinkyFarOfPlayer() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#S    #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#     #   #");
        map.add("#   P     #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.EAST);
    }

    @Test
    public void testArcadeBug() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    S    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#     P   #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.EAST);
    }

    @Test
    public void testPlayerFacingDown() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    P    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#S        #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.EAST);
    }

    @Test
    public void testPlayerFacingDown2() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    P    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#       S #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.WEST);
    }

    @Test
    public void testPlayerFacingDown3() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    P    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#       S #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.WEST);
    }

    @Test
    public void testPlayerFacingDownWithWalls() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    P    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#S#       #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.NORTH});
    }

    @Test
    public void testPlayerFacingWithWalls2() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    P    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#      #S #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.NORTH});
    }

    @Test
    public void testPinkyStuck() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    P    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#       # #");
        map.add("#      #S##");
        map.add("#       # #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }

    @Test
    public void testPlayerFacingEast() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#P        #");
        map.add("#         #");
        map.add("#       S #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void testPlayerFacingEast2() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#       S #");
        map.add("#         #");
        map.add("#         #");
        map.add("#P        #");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testPlayerFacingEast3() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#   S     #");
        map.add("#         #");
        map.add("#         #");
        map.add("#P        #");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testPlayerFacingEast4() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#P        #");
        map.add("#         #");
        map.add("#   S     #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void testPlayerFacingWest() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#       P #");
        map.add("#         #");
        map.add("# S       #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void testPlayerFacingWest2() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("# S       #");
        map.add("#         #");
        map.add("#         #");
        map.add("#        P#");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testPlayerFacingWest3() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    S    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#        P#");
        map.add("#         #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void testPlayerFacingWest4() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#        P#");
        map.add("#         #");
        map.add("#     S   #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.NORTH);
    }

    @Test
    public void testPlayerStuckButFacingSouth() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#   #P#   #");
        map.add("#    #    #");
        map.add("#         #");
        map.add("#         #");
        map.add("#       S #");
        map.add("#         #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.SOUTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.WEST, Direction.NORTH});
    }

    @Test
    public void testPlayerStuckButFacingNorth() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#        S#");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#    #    #");
        map.add("#   #P#   #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.WEST, Direction.SOUTH});
    }

    @Test
    public void testPlayerStuckButFacingNorth2() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#        S#");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("##        #");
        map.add("#P#       #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.WEST});
    }

    @Test
    public void testPlayerStuckButFacingNorth3() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#        S#");
        map.add("#         #");
        map.add("#         #");
        map.add("##        #");
        map.add("#P#       #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.SOUTH, Direction.WEST});
    }

    @Test
    public void testPlayerStuckButFacingNorth4() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#         #");
        map.add("#        S#");
        map.add("#         #");
        map.add("##        #");
        map.add("#P#       #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(Direction.NORTH);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.get()).isIn(new Direction[]{Direction.NORTH, Direction.WEST});
    }

    @Test
    public void testPlayerFacingNowhere() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#         #");
        map.add("#      S  #");
        map.add("#         #");
        map.add("#         #");
        map.add("#         #");
        map.add("#P        #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        player.setDirection(null);
        Pinky pinky = Navigation.findUnitInBoard(Pinky.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = pinky.nextAiMove();

        assertThat(aiDirection.isEmpty()).isTrue();
    }
}
