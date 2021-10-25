package org.rundeck.app.authorization.domain.execution

import com.dtolabs.rundeck.core.authorization.AuthContextProcessor
import grails.compiler.GrailsCompileStatic
import org.rundeck.core.auth.access.AuthActions
import org.rundeck.core.auth.access.AccessLevels
import org.rundeck.core.auth.access.Accessor
import org.rundeck.core.auth.access.BaseAuthorizingIdResource
import org.rundeck.core.auth.AuthConstants
import org.rundeck.core.auth.access.NotFound
import org.rundeck.core.auth.access.UnauthorizedAccess
import rundeck.Execution

import javax.security.auth.Subject

@GrailsCompileStatic
class AppAuthorizingExecution extends BaseAuthorizingIdResource<Execution, ExecIdentifier>
    implements AuthorizingExecution {
    final String resourceTypeName = 'Execution'



    AppAuthorizingExecution(
        final AuthContextProcessor rundeckAuthContextProcessor,
        final Subject subject,
        final ExecIdentifier identifier
    ) {
        super(rundeckAuthContextProcessor, subject, identifier)
    }

    @Override
    protected Map authresMapForResource(final Execution resource) {
        return resource.scheduledExecution ?
               rundeckAuthContextProcessor.authResourceForJob(
                   resource.scheduledExecution.jobName,
                   resource.scheduledExecution.groupPath,
                   resource.scheduledExecution.uuid
               ) :
               AuthConstants.RESOURCE_ADHOC
    }

    @Override
    boolean exists() {
        return Execution.countById(identifier.id.toLong()) == 1
    }

    @Override
    protected Execution retrieve() {
        return Execution.get(identifier.id)
    }


    @Override
    String getResourceIdent() {
        return identifier.id
    }

    @Override
    protected String getProject(final Execution resource) {
        resource.project
    }

    public Execution getReadOrView() throws UnauthorizedAccess, NotFound{
        return access(AccessLevels.APP_READ_OR_VIEW)
    }

    public Execution getKill() throws UnauthorizedAccess, NotFound{
        return access(APP_KILL)
    }

    public Execution getKillAs() throws UnauthorizedAccess, NotFound{
        return access(APP_KILLAS)
    }

}
