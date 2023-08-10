package com.aok.trygdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class TryGDX extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, man, barrel, rock;
	float manX = 0, manY = 0, bounce = 0, gravity = 0.1f;
	int gameState = 0, score = 0, scoredEnemy = 0;

	int numberOfEnemies = 4;
	float[] barrelX = new float[numberOfEnemies];
	float barrelVelocity = 4;
	float distance = 0;

	float[] enemyOffSet = new float[numberOfEnemies];
	Random random;
	Circle manCircle, barrelCircle[];
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	BitmapFont font2;
	BitmapFont font3;


	
	@Override
	public void create () { // Oyun açıldığında olması gerekenleri yazmamız gereken metot
		batch = new SpriteBatch();
		background = new Texture("background.png");
		man = new Texture("walk.gif");
		barrel = new Texture("barrel.png");

		distance = Gdx.graphics.getWidth()/4;

		manX = Gdx.graphics.getWidth()/10;
		manY = Gdx.graphics.getHeight()/15;

		random = new Random();
		shapeRenderer = new ShapeRenderer();

		manCircle = new Circle();
		barrelCircle = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.BLUE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5);

		font3 = new BitmapFont();
		font3 .setColor(Color.WHITE);
		font3.getData().setScale(5);

		for(int i = 0; i < numberOfEnemies; i++){

			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);

			barrelX[i] = (Gdx.graphics.getWidth() - barrel.getWidth()/2 + i * distance);

			barrelCircle[i] = new Circle();

		}




	}

	@Override
	public void render () { //Oyun oynandığo esnada olması gerekenleri yazmamız gereken metot


		batch.begin();

		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1){

			if (barrelX[scoredEnemy] < manX) {
				score++;
				if(scoredEnemy < numberOfEnemies - 1){
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){
				bounce = -5;
			}


			for (int i = 0; i < numberOfEnemies; i++){

				if(barrelX[i] < Gdx.graphics.getWidth() / 15){
					barrelX[i] = barrelX[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);

				}else {
					barrelX[i] -= barrelVelocity;
				}

				batch.draw(barrel, barrelX[i] + enemyOffSet[i], Gdx.graphics.getHeight()/15, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10 );

				barrelCircle[i] = new Circle(barrelX[i] + enemyOffSet[i] + Gdx.graphics.getWidth()/20 ,Gdx.graphics.getHeight()/15 + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/20);

			}





			if (manY > 0 ){
				bounce += gravity;
				manY -= bounce;
			}else {
				gameState = 2;
			}
		}else if(gameState == 0) {

			font3.draw(batch, "Oyuna baslamak icin tiklayin", Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Oyun Bitti. Tekrar oynamak için TIKLA!", Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()){
				gameState = 1;

				manY = Gdx.graphics.getHeight()/15;

				for(int i = 0; i < numberOfEnemies; i++){

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);

					barrelX[i] = (Gdx.graphics.getWidth() - barrel.getWidth()/2 + i * distance);

					barrelCircle[i] = new Circle();

				}

				bounce = 0;
				scoredEnemy = 0;
				score = 0;
			}
		}


		batch.draw(man,manX, manY, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()* 0.9f);

		batch.end();

		manCircle.set(manX +Gdx.graphics.getWidth()/20 , manY + Gdx.graphics.getHeight()/30, Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		//shapeRenderer.setColor(Color.BLUE);
		//shapeRenderer.circle(manCircle.x, manCircle.y, manCircle.radius);


		for (int i=0; i<numberOfEnemies; i++){

			//shapeRenderer.circle(barrelX[i] + enemyOffSet[i] + Gdx.graphics.getWidth()/20 ,Gdx.graphics.getHeight()/15 + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/20);

			if(Intersector.overlaps(manCircle, barrelCircle[i])){

				gameState = 2;

			}

		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}

