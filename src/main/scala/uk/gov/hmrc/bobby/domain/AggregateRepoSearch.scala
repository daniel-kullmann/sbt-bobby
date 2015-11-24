/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.bobby.domain

import sbt.ModuleID
import uk.gov.hmrc.bobby.Helpers

import scala.util.{Success, Try}
import Helpers._

trait AggregateRepoSearch extends RepoSearch{

  def repos:Seq[RepoSearch]

  def search(versionInformation: ModuleID, scalaVersion: Option[String]):Try[Version]={

    val latestVersions: Seq[Try[Version]] = repos.map { r =>
      r.findLatestRevision(versionInformation, scalaVersion)
    }

    val res: Option[Try[Version]] = latestVersions.find(v => v.isSuccess)
    res.toTry(new Exception("Not found")).flatten
  }

}
