package net.spideynn.miner2d;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSave implements Serializable {

	private static final long serialVersionUID = 669254393427980214L;

	public GameSave() {
		this.canreach = true;
		this.lightoutset = new ArrayList<Light>();
		this.camX = 0f;
		this.camY = 0f;
		this.camOffsX = 0f;
		this.camOffsY = 0f;
		this.lcamX = 0f;
		this.lcamY = 0f;
		this.mapWidth = 512;
		this.mapHeight = 8192;
	}

	public boolean canreach = true;

	public List<LivingEntity> entities = new ArrayList<LivingEntity>();

	public List<Light> lightoutset = new ArrayList<Light>();

	public float camX = 0f;
	public float camY = 0f;

	public int grassHeight = 512;

	public float camOffsX = 0f;
	public float camOffsY = 0f;
	public float lcamX = 0f;
	public float lcamY = 0f;

	public List<PickableItem> items = new ArrayList<PickableItem>();

	public Player mp;

	public int mapWidth = 512, mapHeight = 8192;

	public Tile[][] map;

}
