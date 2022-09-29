/*
 * Copyright (c) 2022 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.settings.devices.v2.details.extended

import im.vector.app.core.resources.AppNameProvider
import im.vector.app.core.resources.BuildMeta
import javax.inject.Inject

/**
 * This use case updates if needed the account data event containing extended client info.
 */
class UpdateMatrixClientInfoUseCase @Inject constructor(
        private val appNameProvider: AppNameProvider,
        private val buildMeta: BuildMeta,
        private val getMatrixClientInfoUseCase: GetMatrixClientInfoUseCase,
        private val setMatrixClientInfoUseCase: SetMatrixClientInfoUseCase,
) {

    // TODO add unit tests
    suspend fun execute(): Result<Unit> = runCatching {
        val clientInfo = MatrixClientInfoContent(
                name = appNameProvider.getAppName(),
                version = buildMeta.versionName
        )
        val storedClientInfo = getMatrixClientInfoUseCase.execute()
        if (clientInfo != storedClientInfo) {
            setMatrixClientInfoUseCase.execute(clientInfo)
        }
    }
}
