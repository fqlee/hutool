package com.xiaoleilu.hutool.db.dialect.impl;

import com.xiaoleilu.hutool.db.Page;
import com.xiaoleilu.hutool.db.dialect.DialectName;
import com.xiaoleilu.hutool.db.sql.SqlBuilder;

/**
 * Oracle 方言
 * @author loolly
 *
 */
public class OracleDialect extends AnsiSqlDialect{
	
	public OracleDialect() {
//		wrapper = new Wrapper('"');	//Oracle所有字段名用双引号包围，防止字段名或表名与系统关键字冲突
	}
	
	@Override
	protected SqlBuilder wrapPageSql(SqlBuilder find, Page page) {
		final int[] startEnd = page.getStartEnd();
		return SqlBuilder.create(wrapper)
			.append("SELECT * FROM ( SELECT row_.*, rownum rownum_ from ( ")//
			.append(find)//
			.append(" ) row_ where rownum <= ").append(startEnd[1])//
			.append(") table_alias")//
			.append(" where table_alias.rownum_ >= ").append(startEnd[0]);//
	}
	
	@Override
	public DialectName dialectName() {
		return DialectName.ORACLE;
	}
}
