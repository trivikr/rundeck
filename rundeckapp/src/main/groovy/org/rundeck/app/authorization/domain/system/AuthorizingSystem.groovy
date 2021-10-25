package org.rundeck.app.authorization.domain.system

import groovy.transform.CompileStatic
import org.rundeck.core.auth.AuthConstants
import org.rundeck.core.auth.access.AccessLevels
import org.rundeck.core.auth.access.AuthActions
import org.rundeck.core.auth.access.AuthorizingResource
import org.rundeck.core.auth.access.NotFound
import org.rundeck.core.auth.access.Singleton
import org.rundeck.core.auth.access.UnauthorizedAccess

import static org.rundeck.core.auth.access.AccessLevels.action

@CompileStatic
interface AuthorizingSystem extends AuthorizingResource<Singleton> {

    public static final AuthActions APP_CONFIGURE =
        action(AuthConstants.ACTION_CONFIGURE)
            .or(AccessLevels.ALL_ADMIN)

    public static final AuthActions OPS_ENABLE_EXECUTION =
        action(AuthConstants.ACTION_ENABLE_EXECUTIONS)
            .or(AccessLevels.OPS_ADMIN)

    public static final AuthActions OPS_DISABLE_EXECUTION =
        action(AuthConstants.ACTION_DISABLE_EXECUTIONS)
            .or(AccessLevels.OPS_ADMIN)

    public static final AuthActions READ_OR_ANY_ADMIN =
        action(AuthConstants.ACTION_READ)
            .or(AccessLevels.ALL_ADMIN)

    public static final AuthActions READ_OR_OPS_ADMIN =
        action(AuthConstants.ACTION_READ)
            .or(AccessLevels.OPS_ADMIN)

    /**
     *
     * @return access via READ or ADMIN, OPS_ADMIN or APP_ADMIN
     */
    Singleton getReadOrAnyAdmin() throws UnauthorizedAccess, NotFound

    /**
     *
     * @return access via READ ADMIN or OPS_ADMIN
     */
    Singleton getReadOrOpsAdmin() throws UnauthorizedAccess, NotFound

    Singleton getConfigure() throws UnauthorizedAccess, NotFound

    Singleton getOpsEnableExecution() throws UnauthorizedAccess, NotFound

    Singleton getOpsDisableExecution() throws UnauthorizedAccess, NotFound

}