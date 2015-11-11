package tablealias.web.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tablealias.dto.LineaTiempoDto;

public class LineaTiempoResult implements Result {

	static class ByYear {

		private Map<String, List<String>> data = new LinkedHashMap<String, List<String>>();

		public void addTime(String year, String time) {
			if (!data.containsKey(year)) {
				List<String> yearData = new ArrayList<String>();
				yearData.add(time);
				data.put(year, yearData);
			} else {
				List<String> list = data.get(year);
				list.add(time);
			}
		}

		public Map<String, List<String>> getData() {
			return data;
		}
	}

	private boolean exito;
	private String errorMsg;
	private Map<String, ByYear> result;

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Map<String, ByYear> getResult() {
		return result;
	}

	private Map<String, ByYear> groupByYear(
			Map<String, List<LineaTiempoDto>> result) {
		LinkedHashMap<String, ByYear> result1 = new LinkedHashMap<String, LineaTiempoResult.ByYear>();
		for (Map.Entry<String, List<LineaTiempoDto>> ent : result.entrySet()) {
			String table = ent.getKey();
			if (ent.getValue() != null && ent.getValue().size() == 0) {
				result1.put(table, null);
			} else
				for (LineaTiempoDto dto : ent.getValue()) {
					String time = dto.getDate();
					try {
						String year = time.substring(0, 4);
						if (!result1.containsKey(table)) {
							ByYear yearData = new ByYear();
							yearData.addTime(year, time);
							result1.put(table, yearData);
						} else {
							ByYear byYear = result1.get(table);
							byYear.addTime(year, time);
						}
					} catch (IndexOutOfBoundsException i) {
					}
				}
		}
		return result1;
	}

	public void setResult(Map<String, List<LineaTiempoDto>> result) {
		this.result = groupByYear(result);
	}

}
