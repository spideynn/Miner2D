package net.spideynn.game.miner2d;

public class CollisionDetector {

	public static boolean GetCollision(float playerX, float playerY,
			float playerWidth, float playerHeight, float EnemyX, float EnemyY,
			float EnemyWidth, float EnemyHeight) {
		if ((playerX + playerWidth) > EnemyX && (playerX) < EnemyX + EnemyWidth
				&& (playerY + playerHeight) > EnemyY
				&& (playerY) < EnemyY + EnemyHeight) {
			return true;
		} else {
			return false;
		}
	}

}
