package ytel.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BorderLayout の手助けをします。
 * このクラスインスタンスは、レイアウトされるコンポーネントを仮受けし、{@link BorderLayoutBuilder#layoutComponent(Container)}
 * メソッドで、コンテナの指定された場所にレイアウトします。
 * <p>
 * {@link #add(Component, boolean, Direction...)}でコンポーネントを追加します。
 * <br/>
 * Directionでレイアウト位置を指定します。
 * 位置指定は、先頭から順に適用されます。
 * <example>add(arg, false, Direction.South, Direction.East)</example>
 * の指定の場合、メインコンテナのSouthコンテナを作り、そのSouthコンテナにEastコンテナを作った中に
 * コンポーネントは配置されます。
 * </p>
 * <p>
 * 同一箇所に複数のコンポーネントを指定した場合、GridLayoutで縦一列または、横一列に並べて配置されます。
 * この指定は、{@link #add(Component, boolean, Direction...)}の第二引数で指定します。省略された場合はfalseです。
 * <example>add(arg1, true, Direction.South); add(arg2, true, Direction.South)</example>
 * の場合、arg1とarg2は縦一列です。(falseの場合は横一列です)
 * <br/><br/>
 * 指定は常に上書き処理されます。つまり、最終的に適用されるのは、その場所に最後にaddした時のboolean値です。
 * <remarks>
 * boolean値が省略されたときには、無指定の扱いではなく、<strong>暗黙的にfalse指定</strong>だということに留意してください。<br/>
 * たとえば、縦1列に3つのボタンが並んだアプリケーションに、4つ目のボタンを追加したいとします。
 * このときに、4つ目のaddでbooleanを省略してしまうと横1列に表示されるということです。
 * </remarks>
 * </p>
 * @author 22677478
 *
 */
public class BorderLayoutBuilder {
//	public static void main(String[] args) {
//		javax.swing.JFrame frame = new javax.swing.JFrame();
//		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
//		Container con = frame.getContentPane();
//
//		BorderLayoutBuilder blb = new BorderLayoutBuilder();
//		blb.add(new javax.swing.JLabel("ID:"), Direction.North, Direction.West);
//		blb.add(new javax.swing.JTextArea("b"), Direction.North);
//		blb.add(new javax.swing.JButton("Search"), Direction.North, Direction.East);
//		blb.add(new javax.swing.JList());
//		blb.add(new javax.swing.JButton("OK"), Direction.South, Direction.East);
//		blb.add(new javax.swing.JButton("Cancel"), Direction.South, Direction.East);
//		blb.layoutComponent(con);
//		frame.pack();
//		frame.setVisible(true);
//	}
	public static enum Direction {
		North(BorderLayout.NORTH),
		West(BorderLayout.WEST),
		East(BorderLayout.EAST),
		South(BorderLayout.SOUTH),
		Center(BorderLayout.CENTER);

		private String borderLayoutString;
		Direction(String borderLayoutString) {
			this.borderLayoutString = borderLayoutString;
		}
	}

	private final Map<Direction, BorderLayoutBuilder> componentMap;
	private final List<Component> content;
	private boolean orientationHorizonal;

	public BorderLayoutBuilder() {
		this.content = new ArrayList<Component>();
		this.componentMap = new HashMap<BorderLayoutBuilder.Direction, BorderLayoutBuilder>();
	}
	/**
	 * CENTERにレイアウトされるコンポーネントを引継いでインスタンスを生成します。
	 * レイアウト処理で階層が深くなった場合のためのコンストラクタです。
	 * レイアウト処理で階層が深くなった場合、元の階層に表示されるはずだったコンポーネントは、新たな階層でCENTERに
	 * 保持されます。
	 * @param content
	 */
	protected BorderLayoutBuilder(List<Component> content) {
		this.content = content;
		this.componentMap = new HashMap<BorderLayoutBuilder.Direction, BorderLayoutBuilder>();
	}

	/**
	 * レイアウトするコンポーネントを追加します。
	 * レイアウトされる場所は、{@link Direction}列挙体の可変長引数で指定します。
	 * Directionを省略した場合は、CENTERに配置されます。
	 *
	 * 同一レイヤに複数のコンポーネントが配置された場合の並び方は、横です。
	 * @param arg レイアウトするコンポーネント
	 * @param directions 配置位置。先頭から順に適用されます。
	 */
	public void add(Component arg, Direction... directions) {
		add(arg, false, directions);
	}
	/**
	 * レイアウトするコンポーネントを追加します。
	 * 同一レイヤに複数のコンポーネントが配置された場合の並び方(縦/横)を明確に指定します。
	 *
	 * レイアウトされる場所は、{@link Direction}列挙体の可変長引数で指定します。
	 * Directionを省略した場合は、CENTERに配置されます。
	 * @param arg レイアウトするコンポーネント
	 * @param directions 配置位置。先頭から順に適用されます。
	 */
	public void add(Component arg, boolean orientationHorizonal, Direction... directions) {
		if (directions.length == 0) {
			BorderLayoutBuilder center = componentMap.get(Direction.Center);
			if (center == null) {
				this.orientationHorizonal = orientationHorizonal;
				content.add(arg);
			} else {
				center.add(arg);
			}
		}else {
			Direction dir = directions[0];
			if (componentMap.get(dir) == null) {
				BorderLayoutBuilder blb;
				if (dir == Direction.Center) {
					blb = new BorderLayoutBuilder(this.content);
				} else {
					blb = new BorderLayoutBuilder();
				}
				blb.add(arg, orientationHorizonal, cdr(directions));
				componentMap.put(dir, blb);
			} else {
				BorderLayoutBuilder blb = componentMap.get(dir);
				blb.add(arg, orientationHorizonal, cdr(directions));
			}
		}
	}

	/**
	 * このオブジェクト管理下のコンポーネントを、指定されたコンテナにレイアウトします。
	 * @param container
	 */
	public void layoutComponent(Container container) {
		container.setLayout(new BorderLayout());

		if (componentMap.get(Direction.Center) == null) {
			int mainSize = this.content.size();
			if (mainSize > 1) {
				Container center = new Container();
				if (orientationHorizonal) {
					center.setLayout(new GridLayout(mainSize, 1));
				} else {
					center.setLayout(new GridLayout(1, mainSize));
				}
				for (Component element : this.content) {
					center.add(element);
				}
				container.add(center, BorderLayout.CENTER);
			} else if (mainSize == 1) {
				container.add(this.content.get(0), BorderLayout.CENTER);
			}
		} else {
			setContainerInDirection(container, Direction.Center);
		}
		setContainerInDirection(container, Direction.North);
		setContainerInDirection(container, Direction.South);
		setContainerInDirection(container, Direction.East);
		setContainerInDirection(container, Direction.West);
	}
	private void setContainerInDirection(Container container, Direction d) {
		if (componentMap.get(d) != null) {
			Container temp = new Container();
			componentMap.get(d).layoutComponent(temp);
			container.add(temp, d.borderLayoutString);
		}
	}

	private static Direction[] cdr(Direction[] arg) {
		Direction[] ret = new Direction[arg.length - 1];
		for (int i = 1; i < arg.length; i ++) {
			ret[i-1] = arg[i];
		}
		return ret;
	}
}
