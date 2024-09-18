<#-- このテンプレートに対応するデータモデルのクラスは org.seasar.doma.extension.gen.DaoDesc です -->
<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNames as importName>
import ${importName};
</#list>
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Optional;

/**
 * ${simpleName}
 */
@Dao<#if configClassSimpleName??>(config = ${configClassSimpleName}.class)</#if>
@ConfigAutowireable
public interface ${simpleName} {

<#if entityDesc.idEntityPropertyDescs?size gt 0>
    /**
     * 主キーを指定して${entityDesc.simpleName}を検索します。
<#list entityDesc.idEntityPropertyDescs as property>
     * @param ${property.name} ${entityDesc.simpleName}エンティティの${property.name}
</#list>
     * @return the <#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity
     */
    @Select
    Optional<<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if>> selectById(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}<#if property_has_next>, </#if></#list>);

</#if>
<#if entityDesc.idEntityPropertyDescs?size gt 0 && entityDesc.versionEntityPropertyDesc??>
    /**
<#list entityDesc.idEntityPropertyDescs as property>
     * @param e${property.name}
</#list>
     * @param ${entityDesc.versionEntityPropertyDesc.name}
     * @return the <#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity
     */
    @Select(ensureResult = true)
    <#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> selectByIdAndVersion(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}, </#list>${entityDesc.versionEntityPropertyDesc.propertyClassSimpleName} ${entityDesc.versionEntityPropertyDesc.name});

</#if>
    /**
     * ${entityDesc.simpleName}を登録します。
     * @param entity ${entityDesc.simpleName}エンティティ
     * @return 登録された行数
     */
    @Insert
    int insert(<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity);

    /**
     * ${entityDesc.simpleName}を更新します。
     * @param entity ${entityDesc.simpleName}エンティティ
     * @return 更新された行数
     */
    @Update
    int update(<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity);

    /**
     * ${entityDesc.simpleName}を削除します。
     * @param entity ${entityDesc.simpleName}エンティティ
     * @return 削除された行数
     */
    @Delete
    int delete(<#if entityDesc.entityPrefix??>${entityDesc.entityPrefix}</#if>${entityDesc.simpleName}<#if entityDesc.entitySuffix??>${entityDesc.entitySuffix}</#if> entity);

}