package io.core9.editor.filemanager;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class FileManagerImpl implements FileManager {

	private String base;

	public FileManagerImpl(String base) throws Exception {
		this.base = this.real(base);
		if(this.base == null)  throw new Exception("Base directory does not exist");
		new File(this.base).mkdirs();
	}

	private String realPath(String path){
		try {
			return new File(path).getCanonicalPath();
		} catch (IOException e) {
			return null;
		}
	}

	private String real(String path) throws Exception {

		String temp = realPath(path);
		if(temp == null)  throw new Exception("Path does not exist: " + path);
		if(this.base != null && this.strlen(this.base) != 0) {
			if(this.strpos(temp, this.base)) { throw new Exception("Path is not inside base ("+this.base + "): " + temp); }
		}
		return temp;
	}
	private boolean strpos(String temp, String base2) {
		return temp.toLowerCase().contains(base2.toLowerCase());
	}

	private  int strlen(String base2) {
		return base2.length();
	}

	private void path(String id) {
	}
	private void id(String path) {
	}

	@Override
	public String[] lst(String id, String with_root) {
		return null;
	}
	@Override
	public void data(String id) {
	}
	@Override
	public void create(String id, String name, boolean mkdir) {
	}
	@Override
	public void rename(String id, String name) {
	}
	@Override
	public void remove(String id) {
	}
	@Override
	public void move(String id, String par) {
	}
	@Override
	public void copy(String id, String par) {

	}

	@Override
	public String action(FileManagerRequest request){

		String operation = "";
		switch (operation )
		{
		  case "get_node":

			  ///?operation=get_node&id=assets%2Fbootstrap%2Fcss

			  /*
			   [
{
text: "bootstrap-theme.css",
children: false,
id: "assets/bootstrap/css/bootstrap-theme.css",
type: "file",
icon: "file file-css"
},
{
text: "bootstrap-theme.min.css",
children: false,
id: "assets/bootstrap/css/bootstrap-theme.min.css",
type: "file",
icon: "file file-css"
},
{
text: "bootstrap.css",
children: false,
id: "assets/bootstrap/css/bootstrap.css",
type: "file",
icon: "file file-css"
},
{
text: "bootstrap.min.css",
children: false,
id: "assets/bootstrap/css/bootstrap.min.css",
type: "file",
icon: "file file-css"
}
]
			   */

				//$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				//$rslt = $fs->lst($node, (isset($_GET['id']) && $_GET['id'] === '#'));

			  String id = null;
			  String with_root = null;
			  Object result = this.lst(id, with_root);

		        break;
		   default:
		        break;
		}

		return "";

/*


if(isset($_GET['operation'])) {
	$fs = new fs(dirname(__FILE__) . DIRECTORY_SEPARATOR . 'data' . DIRECTORY_SEPARATOR . 'root' . DIRECTORY_SEPARATOR);
	try {
		$rslt = null;
		switch($_GET['operation']) {
			case 'get_node':
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$rslt = $fs->lst($node, (isset($_GET['id']) && $_GET['id'] === '#'));
				break;
			case "get_content":
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$rslt = $fs->data($node);
				break;
			case 'create_node':
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$rslt = $fs->create($node, isset($_GET['text']) ? $_GET['text'] : '', (!isset($_GET['type']) || $_GET['type'] !== 'file'));
				break;
			case 'rename_node':
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$rslt = $fs->rename($node, isset($_GET['text']) ? $_GET['text'] : '');
				break;
			case 'delete_node':
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$rslt = $fs->remove($node);
				break;
			case 'move_node':
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$parn = isset($_GET['parent']) && $_GET['parent'] !== '#' ? $_GET['parent'] : '/';
				$rslt = $fs->move($node, $parn);
				break;
			case 'copy_node':
				$node = isset($_GET['id']) && $_GET['id'] !== '#' ? $_GET['id'] : '/';
				$parn = isset($_GET['parent']) && $_GET['parent'] !== '#' ? $_GET['parent'] : '/';
				$rslt = $fs->copy($node, $parn);
				break;
			default:
				throw new Exception('Unsupported operation: ' . $_GET['operation']);
				break;
		}
		header('Content-Type: application/json; charset=utf-8');
		echo json_encode($rslt);
	}
	catch (Exception $e) {
		header($_SERVER["SERVER_PROTOCOL"] . ' 500 Server Error');
		header('Status:  500 Server Error');
		echo $e->getMessage();
	}
	die();
}

 */
	}

}
