package xyz.andoroid.graphs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.andoroid.graphs.model.Edge;
import xyz.andoroid.graphs.model.EdgeBuilder;
import xyz.andoroid.graphs.model.Vertex;
import xyz.andoroid.graphs.model.VertexBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Stage stage;
	private ButtonGroup<ImageTextButton> group;

	private EdgeBuilder edgeBuilder;
	private VertexBuilder vertexBuilder;

	private ImageTextButton verticesButton;
	private ImageTextButton edgesButton;
	private TextButton exportButton;

	private SelectBox<String> selectBox;

	private int selectedCircle = -1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage();
		shapeRenderer = new ShapeRenderer();
		edgeBuilder = new EdgeBuilder();
		vertexBuilder = new VertexBuilder(edgeBuilder);
		Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
		verticesButton = new ImageTextButton("Vertices",skin,"radio");
		edgesButton = new ImageTextButton("Edges",skin,"radio");
		exportButton = new TextButton("Export",skin);
		exportButton.setPosition(420+150, 20);
		selectBox = new SelectBox<String>(skin);
		verticesButton.setPosition(20,20);
		edgesButton.setPosition(20, 20+verticesButton.getHeight());
		group = new ButtonGroup<>(verticesButton, edgesButton);
		group.setMaxCheckCount(1);
		selectBox.setItems("List of edges" , "Adjacency matrix", "Adjacency list");
		//selectBox.setItems("List of edges");
		selectBox.setWidth(150);
		selectBox.setPosition(400,40);
		exportButton.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				StringBuilder wrt = new StringBuilder();
				if(selectBox.getSelected().equalsIgnoreCase("List of edges")) {
					wrt.append(vertexBuilder.lastId).append("\n").append(edgeBuilder.edges.size()).append("\n");
					for (Edge edge : edgeBuilder.edges) wrt.append(edge.v1).append(" ").append(edge.v2).append("\n");
					/*wrt.append("\n#PATTERN\n" +
							"#//Vertices are numerated from 0 to n-1\n" +
							"#[AMOUNT OF VERTICES]\n" +
							"#[AMOUNT OF EDGES]\n" +
							"#//Next lines have the same pattern and describe an edge\n" +
							"#[1-st VERTEX OF EDGE] [2-nd VERTEX OF EDGE]\n");*/
				} else if(selectBox.getSelected().equalsIgnoreCase("Adjacency matrix")) {
					wrt.append(vertexBuilder.lastId).append("\n");
					if(vertexBuilder.lastId != 0) {
						int[][] a = new int[vertexBuilder.lastId][vertexBuilder.lastId];
						for(int i=0;i<a.length;i++) Arrays.fill(a[i], 0);
						for(Edge edge : edgeBuilder.edges) {
							a[edge.v1][edge.v2]=1;
							a[edge.v2][edge.v1]=1;
						}
						for(int[] as : a) {
							for(int ass : as) wrt.append(ass).append(" ");
							wrt.append("\n");
						}
						/*wrt.append("\n#PATTERN\n" +
								"#[AMOUNT OF VERTICES]\n" +
								"#//Next n lines are presenting adjacency matrix for graph\n" +
								"#[MATRIX]\n");*/
					}
				} else {
					wrt.append(vertexBuilder.lastId).append("\n");
					List<List<Integer> > lst = new ArrayList<>();
					for(int i=0;i<vertexBuilder.lastId;i++) {
						List<Integer> list = new ArrayList<>();
						for(Edge edge : edgeBuilder.edges) {
							if(edge.v1 == i) list.add(edge.v2);
							else if(edge.v2 == i) list.add(edge.v1);
						}
						lst.add(list);
					}
					for(int i=0;i<lst.size();i++) {
						wrt.append(lst.get(i).size()).append(" ");
						for(int j : lst.get(i)) wrt.append(j).append(" ");
						wrt.append("\n");
					}
					/*wrt.append("#PATTERN\n" +
							"#[AMOUNT OF VERTICES]\n" +
							"#//next lines have the same pattern\n" +
							"#[AMOUNT OF ADJACENT VERTICES] [ID OF ADJACENT VERTICES]");*/
				}
				try {
					FileWriter writer = new FileWriter(new File("graph.txt"));
					writer.write(wrt.toString());
					writer.close();
				} catch (IOException ignored) {}
			}
		});
		stage.addActor(exportButton);
		stage.addActor(verticesButton);
		stage.addActor(edgesButton);
		stage.addActor(selectBox);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		batch.end();
		stage.act();
		stage.draw();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		for(Vertex vertex : vertexBuilder.vertices) {
			if(selectedCircle == vertex.id) shapeRenderer.setColor(Color.RED);
			else shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.circle(vertex.circle.x, vertex.circle.y, vertex.circle.radius);
		}
		shapeRenderer.setColor(Color.WHITE);
		for(Edge edge : edgeBuilder.edges) shapeRenderer.rectLine(
				vertexBuilder.getVertex(edge.v1).circle.x,
				vertexBuilder.getVertex(edge.v1).circle.y,
				vertexBuilder.getVertex(edge.v2).circle.x,
				vertexBuilder.getVertex(edge.v2).circle.y, 3);
		shapeRenderer.end();
	}

	private void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

		if(Gdx.input.isKeyJustPressed(Input.Keys.V)) verticesButton.setChecked(true);
		if(Gdx.input.isKeyJustPressed(Input.Keys.E)) edgesButton.setChecked(true);


		if(group.getChecked() != null && group.getChecked() == verticesButton) {
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && Gdx.graphics.getHeight() - Gdx.input.getY() >= 100) {
				vertexBuilder.build(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY());
			} else if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
				vertexBuilder.delete(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY());
			}
		} else if(group.getChecked() != null && group.getChecked() == edgesButton) {
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				int x = Gdx.input.getX();
				int y = Gdx.graphics.getHeight() - Gdx.input.getY();
				Vertex vertex = vertexBuilder.getVertex(x,y);
				if(vertex != null) {
					if (selectedCircle == -1) {
						selectedCircle = vertex.id;
					} else if(selectedCircle != vertex.id) {
						edgeBuilder.build(selectedCircle, vertex.id);
						selectedCircle = -1;
					}
				}
			} else if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
				int x = Gdx.input.getX();
				int y = Gdx.graphics.getHeight() - Gdx.input.getY();
				Vertex vertex = vertexBuilder.getVertex(x,y);
				if(vertex != null) {
					if (selectedCircle == -1) {
						selectedCircle = vertex.id;
					} else if(selectedCircle != vertex.id) {
						edgeBuilder.delete(selectedCircle, vertex.id);
						selectedCircle = -1;
					}
				}
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
