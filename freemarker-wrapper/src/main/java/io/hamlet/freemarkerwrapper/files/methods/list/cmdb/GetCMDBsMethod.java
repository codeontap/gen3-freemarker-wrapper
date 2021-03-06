package io.hamlet.freemarkerwrapper.files.methods.list.cmdb;

import freemarker.core.Environment;
import freemarker.template.*;
import io.hamlet.freemarkerwrapper.files.meta.cmdb.CMDBMeta;
import io.hamlet.freemarkerwrapper.files.methods.list.GetLayerListMethod;
import io.hamlet.freemarkerwrapper.files.processors.cmdb.CMDBProcessor;
import io.hamlet.freemarkerwrapper.utils.FreemarkerUtil;

import java.util.List;
import java.util.Map;

public class GetCMDBsMethod extends GetLayerListMethod implements TemplateMethodModelEx {

    public TemplateModel exec(List args) throws TemplateModelException {
        meta = new CMDBMeta();
        List<String> lookupDirs = (List<String>) ((DefaultListAdapter) Environment.getCurrentEnvironment().getGlobalVariable("lookupDirs")).getWrappedObject();
        List<String> CMDBNames = (List<String>) ((DefaultListAdapter) Environment.getCurrentEnvironment().getGlobalVariable("CMDBNames")).getWrappedObject();
        Map<String, String> cmdbPathMapping = (Map<String, String>) ((DefaultMapAdapter) Environment.getCurrentEnvironment().getGlobalVariable("cmdbPathMappings")).getWrappedObject();
        String baseCMDB = ((SimpleScalar) Environment.getCurrentEnvironment().getGlobalVariable("baseCMDB")).getAsString();
        TemplateHashModelEx options = (TemplateHashModelEx)args.get(0);
        TemplateModelIterator iterator = options.keys().iterator();
        boolean useCMDBPrefix = Boolean.FALSE;
        boolean activeOnly = Boolean.FALSE;
        while (iterator.hasNext()){
            TemplateModel keyModel = iterator.next();
            String key = keyModel.toString();
            Object keyObj = options.get(key);
            if ("UseCMDBPrefix".equalsIgnoreCase(key)){
                useCMDBPrefix = FreemarkerUtil.getOptionBooleanValue(keyObj);
            }
            else if ("ActiveOnly".equalsIgnoreCase(key)){
                activeOnly = FreemarkerUtil.getOptionBooleanValue(keyObj);
            }
        }
        CMDBMeta cmdbMeta = (CMDBMeta)meta;
        cmdbMeta.setLookupDirs(lookupDirs);
        cmdbMeta.setCMDBs(cmdbPathMapping);
        cmdbMeta.setCMDBNamesList(CMDBNames);
        cmdbMeta.setBaseCMDB(baseCMDB);
        cmdbMeta.setUseCMDBPrefix(useCMDBPrefix);
        cmdbMeta.setActiveOnly(activeOnly);

        layerProcessor = new CMDBProcessor();
        return super.process();
    }
}
