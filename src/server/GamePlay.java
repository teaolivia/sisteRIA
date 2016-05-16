import java.util.HashSet;
import java.util.Iterator;

public class GamePlay {
	Integer dayCount;
	Boolean isDay;
	Boolean isRunning;
	Boolean isStarted;

	static HashSet<player> playerHashSet;

	public GamePlay() {
	if (playerHashSet == null){
		playerHashSet = new HashSet<>();
		}

	if (isRunning == null) {
		isRunning = false;
		}

	if (isDay == null) {
		isDay = true;
	}

	if (isStarted == null){
		isStarted = false;
	}
	}

	public void changePhase() {
        if (!isDay) {
            dayCount++;
        }
        isDay = !isDay;
    }


    public static HashSet<Player> getPlayerHashSet() {
        return playerHashSet;
    }

    public Integer getPlayerId(String udpIpAddress) {
    	Iterator<Player> playerIterator = playerHashSet.iterator();
        Player currentPlayerInIterator;

        while (playerIterator.hasNext()) {
            currentPlayerInIterator = playerIterator.next();
            if (currentPlayerInIterator.getUdpIpAddress().equals(udpIpAddress)){
                return currentPlayerInIterator.getPlayerId();
            }
        }

        return -1;
    }

    public Integer joinGame(String username, String udpIpAddress, Integer udpPortNumber) {
        Player player = new Player(username, udpIpAddress, udpPortNumber);
        playerHashSet.add(player);
        return player.getPlayerId();
    }

    public Boolean readyGame(String udpIpAddress) {
        Integer playerId = getPlayerIdFromUdpIpAddress(udpIpAddress);
        if (playerId < 0) {
            return false;
        }
        else {
            Iterator<Player> playerIterator = playerHashSet.iterator();
            Player playerInIterator;
            while (playerIterator.hasNext()) {
                playerInIterator = playerIterator.next();
                if (playerInIterator.getPlayerId() == playerId) {
                    playerInIterator.readyGame();
                    // CHECK IF GAME IS AVAILABLE TO BE STARTED
                    if (isReadyStart()) {
                        startGame();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public Boolean isReadyStart() {
        // To check whether or not the game is available to start
        // Checking procedure: Making sure all Players are CONNECTED AND THEN NOT LEAVING AND THEN READY.
        Boolean found = false;
        Iterator<Player> playerIterator = playerHashSet.iterator();
        Player currentPlayerInIterator;
        while (!found && playerIterator.hasNext()) {
            currentPlayerInIterator = playerIterator.next();
            if (currentPlayerInIterator.getIsConnected() && !currentPlayerInIterator.getIsLeft() && currentPlayerInIterator.getIsReady()) {
                // PASS (doing nothing) and check next
            }
            else {
                found = true;
            }
        }

        return (!found);
    }

     public Boolean leaveGame(String udpIpAddress) {
        Integer playerId = getPlayerIdFromUdpIpAddress(udpIpAddress);
        if (playerId < 0) {
            return false;
        }
        else {
            Iterator<Player> playerIterator = playerHashSet.iterator();
            Player playerInIterator;
            Boolean found = false;
            while (!found && playerIterator.hasNext()) {
                playerInIterator = playerIterator.next();
                if (playerInIterator.getPlayerId() == playerId) {
                    playerIterator.remove();
                    found = true;
                }
            }
            return found;
        }
    }

    public void startGame() {
        isStarted = true;
    }
}