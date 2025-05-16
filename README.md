# Thirdtool-backend-re-

# thirdtool-backend
깃 관련 간단한 가이드 

우리는 각 기능 단위(Feature)로 브랜치를 생성하고, 작업 후 `main` 브랜치에 Pull Request(PR) 방식으로 병합합니다.  
아래는 개발 중 따라야 할 Git 규칙입니다.

---

## 1. 브랜치 전략

- `main`: 배포용 브랜치 (절대 직접 푸시 금지)
- `feature/기능이름`: 기능 단위 작업 브랜치  
  예: `feature/login`, `feature/user-profile`, `feature/#12-refactor-tag`

> ✅ 하나의 기능이 끝나면 해당 브랜치는 병합 후 삭제

---

## 2. 브랜치 생성 및 이동

```bash
git checkout -b feature/기능이름
```

예:
```bash
git checkout -b feature/login-api
```

---

## 3. 커밋 메시지 규칙

형식:
```
[feature-기능이름] 작업 내용
```

예:
- `[feature-login] 로그인 API 구현`
- `[feature-tag] 해시태그 서비스 로직 분리`

---

## 4. 작업 저장 및 푸시

```bash
git add .
git commit -m "[feature-기능이름] 작업 내용"
git push origin feature/기능이름
```

---

## 5. Pull Request (PR)

1. GitHub에서 `main ← feature/기능이름` 기준 PR 생성
2. 제목: `[feature-기능이름] 작업 내용`
3. 리뷰어에게 코드 리뷰 요청- 반드시 리뷰가 확인이 되었을 때만 main 브랜치에 남길 것 
4. 승인되면 `main` 브랜치에 병합

---

## 6. 충돌 해결

```bash
git pull origin main --rebase
# 충돌 파일 수정
git add .
git rebase --continue
git push -f origin feature/기능이름
```

---

## 7. 기타 규칙

- 공통 설정 파일(`QuerydslConfig`, `SecurityConfig`)은 `global/config` 폴더에 위치
- 모든 기능은 자신의 feature 브랜치에서 구현 후 병합
- 테스트 및 설정 리팩토링은 가능하면 독립된 브랜치로 작업

---

## 🧭 팀 협업 규칙 (Issue / Pull Request / Project)

### 📝 Issue 작성 규칙

이슈를 등록할 때는 아래 형식을 따라주세요:

#### 📋 Checklist
- [ ] 📁 Jira 이슈를 등록했나요?  
  예: [TNT-000](https://jira.example.com/browse/TNT-000)

#### ✅ Tasks
- [ ] 이슈 요약을 간결하게 적어주세요  
  예: 로그인 API 예외처리 추가, 마이페이지 UI 수정 등

#### 🙋🏻 More
- 참고할 내용이나, 연관 이슈/기능, 논의할 사항이 있다면 자유롭게 작성해주세요  
  예: "해당 기능은 #14 이슈와 연동되어야 합니다."

> 💡 **Project 자동화 연동을 위해, 이슈 완료 후 반드시 `Closed` 처리해주세요.**

---

### 🚀 Pull Request 작성 규칙

PR을 올릴 때는 아래 양식을 따라주세요:

#### 🔗 관련 이슈
- 관련된 Jira 또는 GitHub 이슈를 링크해주세요  
  예: `[TNT-001](https://jira.example.com/browse/TNT-001)`

#### ✨ 작업 내용
- 해당 PR에서 구현하거나 수정한 내용을 간결하게 정리해주세요  
  예: "프로필 수정 API 구현", "이메일 인증 기능 추가"

#### ✅ 체크리스트
- [ ] 코드가 정상적으로 컴파일되나요?
- [ ] 테스트 코드를 통과했나요?
- [ ] merge할 브랜치를 정확히 선택했나요? (ex: `main`, `dev`)
- [ ] Label을 지정했나요?

#### 🎃 새롭게 알게된 사항
- 작업하면서 새로 학습한 개념이나 문제 해결 경험이 있다면 적어주세요 (선택 사항)

#### 📋 참고 사항
- 리뷰어가 주의 깊게 봐야 할 부분, 배포 시 유의 사항 등을 작성해주세요  
  예: 외부 API 연동, 예외 처리 주의 등

---

### 📌 프로젝트 사용 시 주의사항

- Issue가 **Close** 상태가 되면, GitHub Project에서도 `"Done"` 칼럼으로 자동 이동됩니다.  
- 수동으로 종료할 경우에도 **반드시 `Closed` 처리**해주세요.

